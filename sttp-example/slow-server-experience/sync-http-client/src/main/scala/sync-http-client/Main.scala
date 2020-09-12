package sync_http_client

import sttp.client._

object Main extends App {

  val backend = HttpURLConnectionBackend()

  val request = basicRequest
    .get(uri"http://localhost:8081")

  println(java.time.ZonedDateTime.now())

  val responses = (1 to 30).map(_ => backend.send(request))

  for {
    r <- responses
  } {
    // println(r)
  }

  backend.close()

  println(java.time.ZonedDateTime.now())

}
