package example

import akka.http.scaladsl.server.{HttpApp, Route}
import com.softwaremill.session.{SessionConfig, SessionManager}
import example.routing._

object Main extends HttpApp with App with GraphqlRoute with LoginRoute with LogoutRoute {

  val sessionConfig = SessionConfig.fromConfig()

  implicit val sessionManager = new SessionManager[Session](sessionConfig)

  def createRoutes()(implicit
      sessionManager: SessionManager[Session]
  ): Route =
    loginRoute() ~
    logoutRoute() ~
    graphQLRoute()

  override def routes: Route = {
    Route.seal {
      createRoutes()
    }
  }

  startServer(host = "0.0.0.0", port = 8080)

}
