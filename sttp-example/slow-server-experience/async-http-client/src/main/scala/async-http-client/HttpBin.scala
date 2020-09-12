package async_http_client

import core.HttpBinResponse
import sttp.client._
import sttp.client.akkahttp._
import sttp.client.circe._

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global

object HttpBin extends App {

  val backend = AkkaHttpBackend()

  val request = basicRequest
    .body("Hello, world!")
    .post(uri"https://httpbin.org/post?hello=world")
    .response(asJson[HttpBinResponse])

  val f = backend
    .send(request)

  Await.ready(f, Duration.Inf)

  for {
    r <- f
  } {
    println(r.body.fold(identity, identity))
  }

  backend.close()
}
