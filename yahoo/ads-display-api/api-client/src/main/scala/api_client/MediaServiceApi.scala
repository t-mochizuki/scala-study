package api_client

import sttp.client._
import sttp.client.circe._

class MediaServiceApi(accessToken: String) {

  implicit val backend = HttpURLConnectionBackend()

  def request(mediaServiceSelector: MediaServiceSelector) =
    basicRequest
      .body(mediaServiceSelector)
      .header("Authorization", s"Bearer $accessToken")
      .header("Content-Type", "application/json")
      .post(uri"https://ads-display.yahooapis.jp/api/v2/MediaService/get")
      .response(asJson[MediaServiceGetResponse])
      .send()

  def response(
      mediaServiceSelector: MediaServiceSelector
  ): Response[Either[ResponseError[io.circe.Error], MediaServiceGetResponse]] =
    try (request(mediaServiceSelector))
    finally (backend.close())

  def mediaServiceGetPost(
      mediaServiceSelector: MediaServiceSelector
  ): Response[Either[ResponseError[io.circe.Error], MediaServiceGetResponse]] = {
    val r = response(mediaServiceSelector)
    println(r)
    r
  }

}
