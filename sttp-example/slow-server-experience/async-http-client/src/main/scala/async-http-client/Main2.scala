package async_http_client

import core.AsyncExecutor
import sttp.client._
import sttp.client.akkahttp._

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

object Main2 extends App {

  implicit val ec = AsyncExecutor()

  val backend = AkkaHttpBackend()

  val request = basicRequest
    .get(uri"http://localhost:8081")

  println(java.time.ZonedDateTime.now())

  val f = Future.traverse((1 to 1000)) {_ =>
    backend.send(request)
  }

  Await.ready(f, Duration.Inf)

  for {
    responses <- f
  } {
    for {
      r <- responses
    } {
      // println(r)
    }
  }

  backend.close()

  println(java.time.ZonedDateTime.now())

  // execution time is about 9s

}
