package example.rest_api.route

import akka.http.scaladsl.server.Directives
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.model.StatusCodes.OK
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
import example.rest_api.component.PersonComponent
import example.rest_api.entity.PersonEntity
import io.circe.syntax._

import scalikejdbc.DB

trait PersonRoute extends Directives with PersonComponent {
  def personRoutes(): Route = {
    // format: off
    path("persons") {
      get {
        parameter("limit".as[Int], "offset".as[Int]) { (limit, offset) =>
          DB.readOnly { implicit session =>
            val list = personHandler.findList(limit, offset)

            complete(
              HttpEntity(ContentTypes.`application/json`, list.asJson.toString)
            )
          }
        }
      } ~
      post {
        entity(as[PersonEntity]) { person =>
          DB.localTx { implicit session =>
            val _ = personHandler.create(person)
          }
          complete(OK)
        }
      } ~
      put {
        entity(as[PersonEntity]) { person =>
          DB.localTx { implicit session =>
            val _ = personHandler.update(person)
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
}
