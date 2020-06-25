package example.gql_server

import akka.http.scaladsl.server.{HttpApp, Route}
import example.core.settings.DBSettings
import example.gql_server.routing.GraphQLRoute

object Main extends HttpApp with App with DBSettings with GraphQLRoute {

  override def routes: Route =
    Route.seal {
      graphQLRoute
    }

  startServer(host = "localhost", port = 8181)

}
