package example.rest_api

import akka.http.scaladsl.server.{HttpApp, Route}
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}

object Main extends HttpApp with App {

  override def routes: Route = {
    // format: off
    path("hello") {
      get {
        complete(
          HttpEntity(ContentTypes.`application/json`, "{}")
        )
      }
    }
    // format: on
  }

  startServer(host = "localhost", port = 8181)

}
