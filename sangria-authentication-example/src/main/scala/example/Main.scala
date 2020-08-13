package example

import akka.http.scaladsl.server.{HttpApp, Route}
import com.softwaremill.session.{SessionConfig, SessionManager}
import com.softwaremill.session.InMemoryRefreshTokenStorage
import example.routing.GraphqlRoute

object Main extends HttpApp with App with GraphqlRoute {

  val sessionConfig = SessionConfig.fromConfig()

  implicit val sessionManager = new SessionManager[Session](sessionConfig)

  implicit val refreshTokenStorage = new InMemoryRefreshTokenStorage[Session] {
    def log(msg: String) = println(msg)
  }

  override def routes: Route = {
    Route.seal {
      graphQLRoute()
    }
  }

  startServer(host = "0.0.0.0", port = 8080)

}
