package sync_http_client

import core.AsyncExecutor
import sttp.client._

import scala.concurrent.duration.Duration
import scala.concurrent.{blocking, Await, Future}

object Main2 extends App {

  implicit val ec = AsyncExecutor()

  val backend = HttpURLConnectionBackend()

  val request = basicRequest
    .get(uri"http://localhost:8081")

  println(java.time.ZonedDateTime.now())

  val fs = (1 to 1000).map {_ =>
    Future(blocking(backend.send(request)))
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
  }

  backend.close()

  println(java.time.ZonedDateTime.now())

  // execution time is about 12s

}
