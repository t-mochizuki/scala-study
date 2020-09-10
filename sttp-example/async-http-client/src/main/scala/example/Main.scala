package example

import io.circe.Decoder
import io.circe.generic.semiauto.deriveDecoder
import sttp.client._
import sttp.client.akkahttp._
import sttp.client.circe._

import scala.concurrent._
import scala.concurrent.ExecutionContext.Implicits.global

object Main extends App {

  case class HttpBinResponse(origin: String, headers: Map[String, String])
  object HttpBinResponse {
    implicit val decoder : Decoder[HttpBinResponse] = deriveDecoder
  }

  val backend = AkkaHttpBackend()

  val request = basicRequest
    .body("Hello, world!")
    .post(uri"https://httpbin.org/post?hello=world")
    .response(asJson[HttpBinResponse])

  val response = backend
    .send(request)

  for {
    r <- response
  } {
    println(r.body.fold(identity, httpBinResponse => httpBinResponse.origin))
    backend.close()
  }
}
