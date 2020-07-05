package example.gql_server.routing

import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.server.Directives
import akka.http.scaladsl.server.Route
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
import example.gql_server.component.GraphQLComponent
import example.gql_server.context.UserContext
import example.gql_server.schema.GraphQLSchema
import io.circe.Json
import io.circe.parser._
import sangria.ast.Document
import sangria.execution.{ErrorWithResolver, Executor, QueryAnalysisError}
import sangria.marshalling.circe._
import sangria.parser.{QueryParser, SyntaxError}
import scala.util.control.NonFatal
import scala.util.{Failure, Success}

trait GraphQLRoute extends GraphQLSchema with GraphQLComponent with Directives {

  def graphQLRoute(): Route =
    path("graphql") {
      post {
        entity(as[Json]) { body =>
          val query = body.hcursor.get[String]("query").toOption
          val operationName = body.hcursor.get[String]("operationName").toOption
          val variablesStr = body.hcursor.get[String]("variables").toOption

          query.map(QueryParser.parse(_)) match {
            case Some(Success(ast)) =>
              variablesStr.map(parse) match {
                case Some(Left(error)) => complete(BadRequest, formatError(error))
                case Some(Right(json)) => executeGraphQL(ast, operationName, json)
                case None =>
                  executeGraphQL(
                    ast,
                    operationName,
                    body.hcursor.get[Json]("variables").toOption getOrElse Json.obj()
                  )
              }
            case Some(Failure(error)) => complete(BadRequest, formatError(error))
            case None => complete(BadRequest, formatError("No query to execute"))
          }
        }
      }
    }

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
      variables: Json
  ): Route =
    extractExecutionContext { implicit ec =>
      complete(
        Executor
          .execute(
            schema,
            query,
            new UserContext {
              val personHandler = personHandlerImpl
            },
            variables = variables,
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
