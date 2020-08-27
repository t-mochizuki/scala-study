package resolve_based_auth.routing

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.{Directives, Route}
import com.softwaremill.session.SessionDirectives.setSession
import com.softwaremill.session.SessionManager
import com.softwaremill.session.SessionOptions.{oneOff, usingHeaders}
import resolve_based_auth.Session

trait LoginRoute extends Directives {

  def loginRoute()(implicit
      sessionManager: SessionManager[Session]
  ): Route =
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
    }

}
