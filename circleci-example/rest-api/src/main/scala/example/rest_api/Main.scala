package example.rest_api

import akka.http.scaladsl.server.{HttpApp, Route}
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.model.StatusCodes.OK
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
import example.core.settings.DBSettings
import example.rest_api.component.PersonComponent
import example.rest_api.entity.PersonEntity
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
      } ~
      post {
        entity(as[PersonEntity]) { person =>
          DB.localTx { implicit session =>
            val _ = personHandler.create(person)
          }
          complete(OK)
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
      } ~
      delete {
        DB.localTx { implicit session =>
          val _ = personHandler.delete(id)
        }
        complete(OK)
      }
    }
    // format: on
  }

  startServer(host = "localhost", port = 8181)

}
