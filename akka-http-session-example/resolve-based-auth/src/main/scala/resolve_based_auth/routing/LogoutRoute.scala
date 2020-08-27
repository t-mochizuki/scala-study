package resolve_based_auth.routing

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.{Directives, Route}
import com.softwaremill.session.SessionDirectives.invalidateSession
import com.softwaremill.session.SessionManager
import com.softwaremill.session.SessionOptions.{oneOff, usingHeaders}
import resolve_based_auth.Session

trait LogoutRoute extends Directives {

  def logoutRoute()(implicit
      sessionManager: SessionManager[Session]
  ): Route =
    path("logout") {
      post {
        invalidateSession(oneOff, usingHeaders) {
          complete(StatusCodes.OK)
        }
      }
    }

}
