package example

import sttp.client._

object Main extends App {

  val backend = HttpURLConnectionBackend()
  val body = """
  {
    "query": "{ __schema { types { name } } }"
}"""
  val request = basicRequest
    .body(body)
    .header("Content-Type", "application/json")
    .post(uri"https://ams-api-stg.central-platform.jp/graphql")

  val response = backend.send(request)

  println(response.header("Content-Length"))

  println(response.body)
}
