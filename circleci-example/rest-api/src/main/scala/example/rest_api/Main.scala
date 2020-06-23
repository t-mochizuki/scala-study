package example.rest_api

import akka.http.scaladsl.server.{HttpApp, Route}
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import example.rest_api.component.PersonComponent

object Main extends HttpApp with App with PersonComponent {

  override def routes: Route = Route.seal {
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
