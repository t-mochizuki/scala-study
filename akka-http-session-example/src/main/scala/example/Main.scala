package example

import akka.http.scaladsl.server.{HttpApp, Route}

object Main extends HttpApp with App {

  override def routes: Route =
    Route.seal {
      path("hello") {
        get {
          complete("Hello, world")
        }
      }
    }

  startServer(host = "0.0.0.0", port = 3000)

}
