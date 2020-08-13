package example.routing

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.{Directives, Route}
import com.softwaremill.session.SessionDirectives.invalidateSession
import com.softwaremill.session.SessionManager
import com.softwaremill.session.SessionOptions.{refreshable, usingCookies}
import com.softwaremill.session.RefreshTokenStorage
import example.Session

trait LogoutRoute extends Directives {

  def logoutRoute()(implicit
      sessionManager: SessionManager[Session],
      refreshTokenStorage: RefreshTokenStorage[Session]
  ): Route =
    extractExecutionContext { implicit ec =>
      path("logout") {
        post {
          invalidateSession(refreshable, usingCookies) {
            complete(StatusCodes.OK)
          }
        }
      }
    }

}
