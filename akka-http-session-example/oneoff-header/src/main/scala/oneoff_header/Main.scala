package oneoff_header

import akka.http.scaladsl.model.{HttpHeader, StatusCodes}
import akka.http.scaladsl.server.{HttpApp, Route}
import com.softwaremill.session.SessionDirectives.{invalidateSession, requiredSession, setSession}
import com.softwaremill.session.SessionOptions.{oneOff, usingHeaders}
import com.softwaremill.session.{SessionConfig, SessionManager, SessionResult}

object Main extends HttpApp with App {

  val sessionConfig = SessionConfig.fromConfig()

  implicit val sessionManager = new SessionManager[Session](sessionConfig)

  def createRoutes()(implicit
      sessionManager: SessionManager[Session]
  ): Route =
    // format: off
    path("login") {
      post {
        formFields("id", "password") {
          case (id, password) =>
            if (id == "admin" && password == "admin") {
              setSession(oneOff, usingHeaders, Session(id)) {
                complete(StatusCodes.OK)
              }
            } else {
              complete(StatusCodes.Unauthorized)
            }
        }
      }
    } ~
    path("logout") {
      post {
        invalidateSession(oneOff, usingHeaders) {
          complete(StatusCodes.OK)
        }
      }
    } ~
    path("hello") {
      get {
        val headerName = sessionManager.config.sessionHeaderConfig.getFromClientHeaderName // Authorization
        val lowerCaseName = headerName.toLowerCase
        extractRequest { httpRequest =>
          val v = httpRequest.headers.collectFirst {
            case HttpHeader(`lowerCaseName`, value) => value
          }.getOrElse("")

          val id = sessionManager.clientSessionManager.decode(v) match {
            case s: SessionResult.Decoded[Session] => s.session.id
            case _ => ""
          }

          requiredSession(oneOff, usingHeaders) { session =>
            complete(s"$id == ${session.id}")
          }
        }
      }
    }
    // format: on

  override def routes: Route = {
    Route.seal {
      createRoutes()
    }
  }

  startServer(host = "localhost", port = 8080)

}
