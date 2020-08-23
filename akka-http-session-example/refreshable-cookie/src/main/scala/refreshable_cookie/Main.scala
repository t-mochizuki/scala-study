package refreshable_cookie

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.{HttpApp, Route}
import com.softwaremill.session.SessionDirectives.{invalidateSession, requiredSession, setSession}
import com.softwaremill.session.SessionOptions.{refreshable, usingCookies}
import com.softwaremill.session.{SessionConfig, SessionManager}
import com.softwaremill.session.RefreshTokenStorage

import scala.concurrent.ExecutionContext

object Main extends HttpApp with App {

  val sessionConfig = SessionConfig.fromConfig()

  implicit val sessionManager = new SessionManager[Session](sessionConfig)

  implicit val refreshTokenStorage = new InMemoryDBRefreshTokenStorage {
    def log(msg: String) = println(msg)
  }

  def createRoutes()(implicit
      sessionManager: SessionManager[Session],
      refreshTokenStorage: RefreshTokenStorage[Session],
      ec: ExecutionContext
  ): Route =
    // format: off
    path("login") {
      post {
        formFields("id", "password") {
          case (id, password) =>
            if (id == "admin" && password == "admin") {
              setSession(refreshable, usingCookies, Session(id)) {
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
        invalidateSession(refreshable, usingCookies) {
          complete(StatusCodes.OK)
        }
      }
    } ~
    path("hello") {
      get {
        requiredSession(refreshable, usingCookies) { session =>
          complete(s"Hello, ${session.id}")
        }
      }
    }
    // format: on

  override def routes: Route = {
    implicit val ec = systemReference.get.dispatcher

    Route.seal {
      createRoutes()
    }
  }

  startServer(host = "localhost", port = 8080)

}
