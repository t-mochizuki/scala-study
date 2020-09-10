package api_client

import io.circe.generic.semiauto._
import io.circe.{Decoder, Encoder}

case class CampaignServiceValue(campaign: Option[Campaign], operationSucceeded: Option[Boolean], errors: Option[Seq[Error]])

object CampaignServiceValue {
  implicit val encoder: Encoder[CampaignServiceValue] = deriveEncoder
  implicit val decoder: Decoder[CampaignServiceValue] = deriveDecoder
}
