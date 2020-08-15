package csrf

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.{HttpApp, Route}
import com.softwaremill.session.CsrfDirectives.{randomTokenCsrfProtection, setNewCsrfToken}
import com.softwaremill.session.CsrfOptions.checkHeader
import com.softwaremill.session.SessionDirectives.{invalidateSession, requiredSession, setSession}
import com.softwaremill.session.SessionOptions.{oneOff, usingCookies}
import com.softwaremill.session.{SessionConfig, SessionManager}

object Main extends HttpApp with App {

  val sessionConfig = SessionConfig.fromConfig()

  implicit val sessionManager = new SessionManager[Session](sessionConfig)

  def createRoutes()(implicit
      sessionManager: SessionManager[Session]
  ): Route =
    // format: off
    randomTokenCsrfProtection(checkHeader) {
      path("csrf") {
        get {
          complete(StatusCodes.OK)
        }
      } ~
      path("login") {
        post {
          formFields("id", "password") { case (id, password) =>
            if (id == "admin" && password == "admin") {
              setSession(oneOff, usingCookies, Session(id)) {
                setNewCsrfToken(checkHeader) { ctx =>
                  ctx.complete(StatusCodes.OK)
                }
              }
            } else {
              complete(StatusCodes.Unauthorized)
            }
          }
        }
      } ~
      path("logout") {
        post {
          requiredSession(oneOff, usingCookies) { _ =>
            invalidateSession(oneOff, usingCookies) { ctx =>
              ctx.complete(StatusCodes.OK)
            }
          }
        }
      } ~
      path("hello") {
        get {
          requiredSession(oneOff, usingCookies) { session => ctx =>
            ctx.complete(s"Hello, ${session.id}")
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

  startServer(host = "0.0.0.0", port = 8080)

}
