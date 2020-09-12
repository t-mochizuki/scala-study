package async_http_client

import core.SyncExecutor
import io.circe.Decoder
import io.circe.generic.semiauto.deriveDecoder
import sttp.client._
import sttp.client.akkahttp._
import sttp.client.circe._

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

object Main3 extends App {

  implicit val ec = SyncExecutor()

  val backend = AkkaHttpBackend()

  val request = basicRequest
    .get(uri"http://localhost:8081")

  println(java.time.ZonedDateTime.now())

  val fs = (1 to 1000).map{_ =>
    backend.send(request)
  }

  val f = Future.sequence(fs)

  Await.ready(f, Duration.Inf)

  for {
    responses <- f
  } {
    for {
      r <- responses
    } {
      // println(r)
    }
    backend.close()
    println(java.time.ZonedDateTime.now())
  }

}
