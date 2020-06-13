package example.foo_client

import akka.actor.ActorSystem
import akka.http.scaladsl.model.headers.Accept
import akka.http.scaladsl.model.{HttpHeader, HttpMethods, HttpRequest, MediaTypes, Uri}
import akka.http.scaladsl.unmarshalling.Unmarshal
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
import example.base.dao.PersonEntity
import example.base.http.Client
import example.base.logging.Logger
import io.circe.generic.auto._

import scala.concurrent.Await
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext
import scala.util.{Failure, Success}

object Main extends App with Logger {
  val uri = "http://localhost:8181/hello"

  implicit val system: ActorSystem = ActorSystem()
  implicit val executionContext: ExecutionContext = system.dispatcher

  val acceptHeader: HttpHeader = Accept(MediaTypes.`application/json`)
  val headers: Seq[HttpHeader] = Seq(acceptHeader)
  val httpRequest = HttpRequest(method = HttpMethods.GET, uri = Uri(uri), headers = headers)

  Client
    .send(httpRequest)
    .onComplete[Any] {
      case Success(res) => {
        Unmarshal(res.entity).to[PersonEntity].foreach(x => println(PersonEntity.encoder(x)))
        println(res.entity.discardBytes())
        println(system.terminate())
        Await.ready(system.whenTerminated, 10.seconds)
      }
      case Failure(e) => logger.error(e.getMessage())
    }

}
