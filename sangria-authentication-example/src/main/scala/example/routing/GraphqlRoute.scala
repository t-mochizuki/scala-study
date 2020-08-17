package example.routing

import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.server.Directives
import akka.http.scaladsl.server.Route
import com.softwaremill.session.SessionManager
import de.heikoseeberger.akkahttpcirce.ErrorAccumulatingCirceSupport._
import example.Session
import example.context.UserContext
import example.repository.NumberRepo
import example.schema.GraphqlSchema
import io.circe.Json
import io.circe.parser._
import sangria.ast.Document
import sangria.execution.{ErrorWithResolver, ExceptionHandler, Executor, HandledException, QueryAnalysisError}
import sangria.marshalling.circe._
import sangria.parser.{QueryParser, SyntaxError}

import scala.util.control.NonFatal
import scala.util.{Failure, Success}

trait GraphqlRoute extends GraphqlSchema with Directives {

  val exceptionHandler = ExceptionHandler {
    case (_, AuthenticationException(message)) => HandledException(message)
  }

  def graphQLRoute()(implicit
      sessionManager: SessionManager[Session]
  ): Route =
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
  )(implicit
      sessionManager: SessionManager[Session]
  ): Route =
    extractExecutionContext { implicit ec =>

      optionalHeaderValueByName(sessionManager.config.sessionHeaderConfig.getFromClientHeaderName) { token =>

        complete(
          Executor
            .execute(
              schema,
              query,
              UserContext(token, new NumberRepo),
              variables = variables,
              operationName = operationName,
              exceptionHandler = exceptionHandler,
              middleware = Nil
            )
            .map(OK -> _)
            .recover {
              case error: QueryAnalysisError => BadRequest -> error.resolveError
              case error: ErrorWithResolver => InternalServerError -> error.resolveError
            }
        )
      }
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
