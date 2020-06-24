package example.graphql_server

import akka.http.scaladsl.server.{HttpApp, Route}
import akka.http.scaladsl.model.StatusCodes.OK

object Main extends HttpApp with App {

  override def routes: Route =
    Route.seal {
      path("graphql") {
        get {
          complete(OK)
        }
      }
    }

  startServer(host = "localhost", port = 8181)

}
