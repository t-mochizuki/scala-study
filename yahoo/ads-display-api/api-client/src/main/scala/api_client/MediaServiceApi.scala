package api_client

import sttp.client._
import sttp.client.circe._

class MediaServiceApi(accessToken: String) {

  implicit val backend = HttpURLConnectionBackend()

  def getRequest(mediaServiceSelector: MediaServiceSelector) =
    basicRequest
      .body(mediaServiceSelector)
      .header("Authorization", s"Bearer $accessToken")
      .header("Content-Type", "application/json")
      .post(uri"https://ads-display.yahooapis.jp/api/v2/MediaService/get")
      .response(asJson[MediaServiceGetResponse])
      .send()

  def getResponse(
      mediaServiceSelector: MediaServiceSelector
  ): Response[Either[ResponseError[io.circe.Error], MediaServiceGetResponse]] =
    try (getRequest(mediaServiceSelector))
    finally (backend.close())

  def mediaServiceGetPost(
      mediaServiceSelector: MediaServiceSelector
  ): Response[Either[ResponseError[io.circe.Error], MediaServiceGetResponse]] = {
    val r = getResponse(mediaServiceSelector)
    println(r)
    r
  }

  def addRequest(mediaServiceOperation: MediaServiceOperation) =
    basicRequest
      .body(mediaServiceOperation)
      .header("Authorization", s"Bearer $accessToken")
      .header("Content-Type", "application/json")
      .post(uri"https://ads-display.yahooapis.jp/api/v2/MediaService/add")
      .response(asJson[MediaServiceMutateResponse])
      .send()

  def addResponse(
      mediaServiceOperation: MediaServiceOperation
  ): Response[Either[ResponseError[io.circe.Error], MediaServiceMutateResponse]] =
    try (addRequest(mediaServiceOperation))
    finally (backend.close())

  def mediaServiceAddPost(
    mediaServiceOperation: MediaServiceOperation
  ): Response[Either[ResponseError[io.circe.Error], MediaServiceMutateResponse]] = {
    val r = addResponse(mediaServiceOperation)
    println(r)
    r
  }

}
