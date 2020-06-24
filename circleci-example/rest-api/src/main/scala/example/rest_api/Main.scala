package example.rest_api

import akka.http.scaladsl.server.{HttpApp, Route}
import example.core.settings.DBSettings
import example.rest_api.route.PersonRoute

object Main extends HttpApp with App with DBSettings with PersonRoute {

  override def routes: Route =
    Route.seal {
      personRoutes
    }

  startServer(host = "localhost", port = 8181)

}
