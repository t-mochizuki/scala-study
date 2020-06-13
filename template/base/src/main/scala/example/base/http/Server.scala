package example.base.http

import java.time.{ZoneId, ZonedDateTime}

import akka.actor.ActorSystem
import akka.http.scaladsl.model.headers.Accept
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.model.{HttpHeader, HttpMethods, HttpRequest, MediaTypes, Uri}
import akka.http.scaladsl.server.{HttpApp, Route}
import example.base.dao.PersonEntity
import example.base.http.Client
import example.base.logging.Logger

class Server extends HttpApp with Logger {

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
    } ~
    path("foo") {
      get {
        extractActorSystem { implicit system =>
          extractExecutionContext { implicit ec =>
            val uri = "http://localhost:8181/hello"
            val acceptHeader: HttpHeader = Accept(MediaTypes.`application/json`)
            val headers: Seq[HttpHeader] = Seq(acceptHeader)
            val httpRequest = HttpRequest(method = HttpMethods.GET, uri = Uri(uri), headers = headers)
            complete(Client.send(httpRequest))
          }
        }
      }
    }

  def start(): Unit = {
    startServer(host = "localhost", port = 8181)
  }

}
