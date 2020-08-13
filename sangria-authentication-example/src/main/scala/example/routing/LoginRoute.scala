package example.routing

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.{Directives, Route}
import com.softwaremill.session.SessionDirectives.setSession
import com.softwaremill.session.SessionManager
import com.softwaremill.session.SessionOptions.{refreshable, usingCookies}
import com.softwaremill.session.RefreshTokenStorage
import example.Session

trait LoginRoute extends Directives {

  def loginRoute()(implicit
      sessionManager: SessionManager[Session],
      refreshTokenStorage: RefreshTokenStorage[Session]
  ): Route =
    extractExecutionContext { implicit ec =>
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
      }
    }

}
