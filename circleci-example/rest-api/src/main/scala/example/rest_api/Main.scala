package example.rest_api

import akka.http.scaladsl.server.{HttpApp, Route}
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import example.core.settings.DBSettings
import example.rest_api.component.PersonComponent
import io.circe.syntax._

import scalikejdbc.DB

object Main extends HttpApp with App with PersonComponent with DBSettings {

  override def routes: Route = Route.seal {
    // format: off
    path("persons") {
      get {
        DB.readOnly { implicit session =>
          val list = personHandler.findList(10, 0)

          complete(
            HttpEntity(ContentTypes.`application/json`, list.asJson.toString)
          )
        }
      }
    } ~
    path("persons" / IntNumber) { id =>
      get {
        DB.readOnly { implicit session =>
          val option = personHandler.findById(id)

          complete(
            HttpEntity(ContentTypes.`application/json`, option.asJson.toString)
          )
        }
      }
    }
    // format: on
  }

  startServer(host = "localhost", port = 8181)

}
