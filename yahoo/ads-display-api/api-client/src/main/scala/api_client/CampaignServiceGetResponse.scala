package api_client

import io.circe.generic.semiauto._
import io.circe.{Decoder, Encoder}

case class CampaignServiceGetResponse(rid: String, rval: CampaignServicePage)

object CampaignServiceGetResponse {
  implicit val encoder: Encoder[CampaignServiceGetResponse] = deriveEncoder
  implicit val decoder: Decoder[CampaignServiceGetResponse] = deriveDecoder
}
