package example

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.{HttpApp, Route}
import com.softwaremill.session.CsrfDirectives.{setNewCsrfToken, randomTokenCsrfProtection}
import com.softwaremill.session.CsrfOptions.checkHeader
import com.softwaremill.session.SessionDirectives.{setSession, invalidateSession, requiredSession}
import com.softwaremill.session.SessionOptions.{oneOff, usingCookies}
import com.softwaremill.session.{SessionConfig, SessionManager}

object Main extends HttpApp with App {

  val sessionConfig = SessionConfig.fromConfig()

  implicit val sessionManager = new SessionManager[Session](sessionConfig)

  def createRoutes()(implicit sessionManager: SessionManager[Session]): Route =
    path("login") {
      post {
        formFields("id", "password") { case (id, password) =>
          if (id == "admin" && password == "admin") {
            setSession(oneOff, usingCookies, Session(id)) {
              setNewCsrfToken(checkHeader) {
                complete(StatusCodes.OK)
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
        invalidateSession(oneOff, usingCookies) {
          complete(StatusCodes.OK)
        }
      }
    } ~
    path("hello") {
      get {
        requiredSession(oneOff, usingCookies) { session =>
          complete(s"Hello, ${session.id}")
        }
      }
    }

  override def routes: Route =
    Route.seal {
      randomTokenCsrfProtection(checkHeader) {
        createRoutes()
      }
    }

  startServer(host = "0.0.0.0", port = 3000)

}