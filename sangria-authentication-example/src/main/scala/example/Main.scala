package example

import akka.http.scaladsl.server.{HttpApp, Route}
import ch.megard.akka.http.cors.scaladsl.CorsDirectives.cors
import com.softwaremill.session.{SessionConfig, SessionManager}
import example.routing._

object Main extends HttpApp with App with GraphqlRoute with LoginRoute with LogoutRoute {

  val sessionConfig = SessionConfig.fromConfig()

  implicit val sessionManager = new SessionManager[Session](sessionConfig)

  def createRoutes()(implicit
      sessionManager: SessionManager[Session]
  ): Route =
    // format: off
    loginRoute() ~
    logoutRoute() ~
    graphQLRoute()
    // format: on

  override def routes: Route = {
    Route.seal {
      cors() {
        createRoutes()
      }
    }
  }

  startServer(host = "localhost", port = 8080)

}
