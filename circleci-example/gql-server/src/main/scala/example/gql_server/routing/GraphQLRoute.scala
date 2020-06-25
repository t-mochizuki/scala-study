package example.gql_server.routing

import akka.http.scaladsl.server.Directives
import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.server.Route
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
import example.rest_api.boundary.db.PersonDao
import example.gql_server.repository.PersonRepo
import example.gql_server.schema.PersonSchema
import io.circe.Json
import sangria.ast.Document
import sangria.execution.{ErrorWithResolver, Executor, QueryAnalysisError}
import sangria.parser.{QueryParser, SyntaxError}
import sangria.marshalling.InputUnmarshaller.emptyMapVars
import sangria.schema._
import sangria.marshalling.circe._
import scala.util.control.NonFatal
import scala.util.{Failure, Success}

trait GraphQLRoute extends PersonSchema with Directives {

  def graphQLRoute(): Route =
      path("graphql") {
        post {
          entity(as[String]) { query =>
            val operationName = None
            QueryParser.parse(query) match {
              case Success(ast) => executeGraphQL(ast, operationName, Json.obj(), false)
              case Failure(error) => complete(BadRequest, formatError(error))
            }
          }
        }
      }

  val schema = Schema(PersonQueryType, Some(PersonMutationType))

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

}
