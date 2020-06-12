package example.base.http

import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.{HttpApp, Route}
import example.base.dao.PersonEntity
import java.time.{ZoneId, ZonedDateTime}

class Server extends HttpApp {

  val zonedDateTime = ZonedDateTime.of(2020, 6, 8, 0, 0, 0, 0, ZoneId.systemDefault)
  val mother = PersonEntity("keiko", 23, zonedDateTime)
  val input = PersonEntity.encoder(mother).toString

  override def routes: Route =
    path("hello") {
      get {
        complete(
          HttpEntity(ContentTypes.`application/json`, input)
        )
      }
    }

  def start(): Unit = {
    startServer(host = "localhost", port = 8181)
  }

}
