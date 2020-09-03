package api_client

import sttp.client._
import sttp.client.circe._

class CampaignServiceApi(accessToken: String) {

  implicit val backend = HttpURLConnectionBackend()

  def request(campaignServiceSelector: CampaignServiceSelector) =
    basicRequest
      .body(campaignServiceSelector)
      .header("Authorization", s"Bearer $accessToken")
      .header("Content-Type", "application/json")
      .post(uri"https://ads-display.yahooapis.jp/api/v2/CampaignService/get")
      .send()

  def response(
      campaignServiceSelector: CampaignServiceSelector
  ): Response[Either[String, String]] =
    try (request(campaignServiceSelector))
    finally (backend.close())

  def campaignServiceGetPost(
      campaignServiceSelector: CampaignServiceSelector
  ): Response[Either[String, String]] = {
    val r = response(campaignServiceSelector)
    println(r)
    r
  }

}
