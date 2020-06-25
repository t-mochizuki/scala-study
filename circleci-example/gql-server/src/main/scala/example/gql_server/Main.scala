package example.gql_server

import akka.Done
import akka.actor.ActorSystem
import akka.http.scaladsl.server.{HttpApp, Route}
import example.core.settings.DBSettings
import example.gql_server.routing.GraphQLRoute
import scala.util.Try
import scalikejdbc.config.DBs

object Main extends HttpApp with App with DBSettings with GraphQLRoute {

  override def postServerShutdown(attempt: Try[Done], system: ActorSystem): Unit = {
    super.postServerShutdown(attempt, system)
    DBs.closeAll()
  }

  override def routes: Route =
    Route.seal {
      graphQLRoute
    }

  startServer(host = "localhost", port = 8181)

}
