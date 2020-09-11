package async_http_client

import java.util.concurrent.ForkJoinPool

import io.circe.Decoder
import io.circe.generic.semiauto.deriveDecoder
import sttp.client._
import sttp.client.akkahttp._
import sttp.client.circe._

import scala.concurrent._

object Main extends App {

  val pool = new ForkJoinPool
  implicit val ec = ExecutionContext.fromExecutorService(pool)

  val backend = AkkaHttpBackend()

  val request = basicRequest
    .get(uri"http://localhost:8081")

  println(java.time.ZonedDateTime.now())

  val fs = (1 to 30).map{_ =>
    backend.send(request)
  }

  val f = Future.sequence(fs)

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
