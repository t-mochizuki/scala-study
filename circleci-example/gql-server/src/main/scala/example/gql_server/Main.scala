package example.gql_server

import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.server.{HttpApp, Route}
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
import example.rest_api.boundary.db.PersonDao
import example.gql_server.repository.PersonRepo
import example.gql_server.schema.PersonSchema
import example.core.settings.DBSettings
import io.circe.Json
import sangria.ast.Document
import sangria.execution.Executor
import sangria.execution.deferred.DeferredResolver
import sangria.execution.{ErrorWithResolver, Executor, QueryAnalysisError}
import sangria.parser.{QueryParser, SyntaxError}
import sangria.marshalling.InputUnmarshaller.emptyMapVars
import sangria.schema._
import sangria.marshalling.circe._
import scala.util.control.NonFatal
import scala.util.{Failure, Success}

object Main extends HttpApp with App with PersonSchema with DBSettings {

  val Id = Argument("id", IntType)

  @SuppressWarnings(Array("org.wartremover.warts.Any"))
  val QueryType = ObjectType(
    "Query",
    fields[PersonRepo, Unit](
      Field(
        "person",
        OptionType(PersonType),
        arguments = Id :: Nil,
        resolve = c => c.ctx.person(c arg Id)
      ),
      Field("persons", ListType(PersonType), resolve = _.ctx.persons)
    )
  )

  val PersonArg = Argument("person", PersonInputType)

  @SuppressWarnings(Array("org.wartremover.warts.Any"))
  val MutationType = ObjectType(
    "Mutation",
    fields[PersonRepo, Unit](
      Field(
        "addPerson",
        OptionType(IntType),
        arguments = PersonArg :: Nil,
        resolve = c => c.ctx.addPerson(c arg PersonArg)
      ),
      Field(
        "updatePerson",
        OptionType(IntType),
        arguments = PersonArg :: Nil,
        resolve = c => c.ctx.updatePerson(c arg PersonArg)
      ),
      Field(
        "deletePerson",
        OptionType(BooleanType),
        arguments = Id :: Nil,
        resolve = c => c.ctx.deletePerson(c arg Id)
      )
    )
  )

  val schema = Schema(QueryType, Some(MutationType))

  @SuppressWarnings(
    Array(
      "org.wartremover.warts.Any",
      "org.wartremover.warts.Product",
      "org.wartremover.warts.Serializable"
    )
  )
  def executeGraphQL(
      query: Document,
      operationName: Option[String],
      variables: Json,
      tracing: Boolean
  ) =
    extractExecutionContext { implicit ec =>
      complete(
        Executor
          .execute(
            schema,
            query,
            new PersonRepo(new PersonDao),
            variables = emptyMapVars,
            operationName = operationName,
            middleware = Nil
          )
          .map(OK -> _)
          .recover {
            case error: QueryAnalysisError => BadRequest -> error.resolveError
            case error: ErrorWithResolver => InternalServerError -> error.resolveError
          }
      )
    }

  @SuppressWarnings(Array("org.wartremover.warts.Throw"))
  def formatError(error: Throwable): Json =
    error match {
      case syntaxError: SyntaxError =>
        Json.obj(
          "errors" -> Json.arr(
            Json.obj(
              "message" -> Json.fromString(syntaxError.getMessage),
              "locations" -> Json.arr(
                Json.obj(
                  "line" -> Json.fromBigInt(syntaxError.originalError.position.line),
                  "column" -> Json.fromBigInt(syntaxError.originalError.position.column)
                )
              )
            )
          )
        )
      case NonFatal(e) =>
        formatError(e.getMessage)
      case e =>
        throw e
    }

  def formatError(message: String): Json =
    Json.obj("errors" -> Json.arr(Json.obj("message" -> Json.fromString(message))))

  override def routes: Route =
    Route.seal {
      path("graphql") {
        parameter("query".as[String]) { (query) =>
          val operationName = None
          QueryParser.parse(query) match {
            case Success(ast) => executeGraphQL(ast, operationName, Json.obj(), false)
            case Failure(error) => complete(BadRequest, formatError(error))
          }
        }
      }
    }

  startServer(host = "localhost", port = 8181)

}
