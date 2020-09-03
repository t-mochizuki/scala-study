package api_client

import sttp.client._

class CampaignServiceApi(accessToken: String) {

  val backend = HttpURLConnectionBackend()

  def body(campaignServiceSelector: CampaignServiceSelector): String =
    CampaignServiceSelector.encoder(campaignServiceSelector).toString

  def request(campaignServiceSelector: CampaignServiceSelector) =
    basicRequest
      .body(body(campaignServiceSelector))
      .header("Authorization", s"Bearer $accessToken")
      .header("Content-Type", "application/json")
      .post(uri"https://ads-display.yahooapis.jp/api/v2/CampaignService/get")

  def response(campaignServiceSelector: CampaignServiceSelector): Response[Either[String, String]] =
    backend.send(request(campaignServiceSelector))

  def campaignServiceGetPost(campaignServiceSelector: CampaignServiceSelector): String = {
    val s = response(campaignServiceSelector)
    println(s.body.getOrElse(""))
    println(s.body.left.getOrElse(""))
    s.body.getOrElse("")
  }

}
