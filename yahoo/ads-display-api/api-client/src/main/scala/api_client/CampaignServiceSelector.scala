package api_client

import io.circe.generic.semiauto._
import io.circe.{Decoder, Encoder}

case class CampaignServiceSelector(
    accountId: Long,
    containsLabelIdFlg: Boolean,
    startIndex: Int,
    numberResults: Int
)

object CampaignServiceSelector {
  implicit val encoder: Encoder[CampaignServiceSelector] = deriveEncoder
  implicit val decoder: Decoder[CampaignServiceSelector] = deriveDecoder
}
