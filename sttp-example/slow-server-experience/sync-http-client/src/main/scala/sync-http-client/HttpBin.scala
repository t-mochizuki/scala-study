package sync_http_client

import core.HttpBinResponse
import sttp.client._
import sttp.client.akkahttp._
import sttp.client.circe._

object HttpBin extends App {

  val backend = HttpURLConnectionBackend()
  val request = basicRequest
    .body("Hello, world!")
    .post(uri"https://httpbin.org/post?hello=world")
    .response(asJson[HttpBinResponse])

  val response = backend.send(request)

  println(response.body.fold(identity, identity))

  backend.close()

}
