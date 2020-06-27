package example.rest_server

import akka.Done
import akka.actor.ActorSystem
import akka.http.scaladsl.server.{HttpApp, Route}
import example.core.settings.DBSettings
import example.rest_server.routing.PersonRoute
import scala.util.Try
import scalikejdbc.config.DBs

object Main extends HttpApp with App with DBSettings with PersonRoute {

  override def postServerShutdown(attempt: Try[Done], system: ActorSystem): Unit = {
    super.postServerShutdown(attempt, system)
    DBs.closeAll()
  }

  override def routes: Route =
    Route.seal {
      personRoutes
    }

  startServer(host = "localhost", port = 8181)

}
