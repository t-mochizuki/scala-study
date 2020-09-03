package api_client

import io.circe.generic.semiauto._
import io.circe.{Decoder, Encoder}

case class CampaignServiceValue(campaign: Campaign, errors: Seq[Error], operationSucceeded: Boolean)

object CampaignServiceValue {
  implicit val encoder: Encoder[CampaignServiceValue] = deriveEncoder
  implicit val decoder: Decoder[CampaignServiceValue] = deriveDecoder
}
