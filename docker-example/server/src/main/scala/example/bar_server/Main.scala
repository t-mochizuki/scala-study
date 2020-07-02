package example.server

import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.{HttpApp, Route}

object Main extends HttpApp with App {

  override def routes: Route = {
    path("hello") {
      get {
        complete(
          HttpEntity(ContentTypes.`application/json`, "Hello, world!")
        )
      }
    }
  }

  startServer(host = "localhost", port = 8181)

}
