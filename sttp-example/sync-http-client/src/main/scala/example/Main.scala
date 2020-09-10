package example

import sttp.client._

object Main extends App {

  val backend = HttpURLConnectionBackend()
  val request = basicRequest
    .body("Hello, world!")
    .post(uri"https://httpbin.org/post?hello=world")

  val response = backend.send(request)

  println(response.header("Content-Length"))

  println(response.body)
}
