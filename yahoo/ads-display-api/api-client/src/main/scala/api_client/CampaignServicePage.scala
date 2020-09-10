package api_client

import io.circe.generic.semiauto._
import io.circe.{Decoder, Encoder}

case class CampaignServicePage(totalNumEntries: Int, values: Option[Seq[CampaignServiceValue]])

object CampaignServicePage {
  implicit val encoder: Encoder[CampaignServicePage] = deriveEncoder
  implicit val decoder: Decoder[CampaignServicePage] = deriveDecoder
}
