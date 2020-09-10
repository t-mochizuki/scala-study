package api_client

import io.circe.generic.semiauto._
import io.circe.{Decoder, Encoder}

case class Campaign(accountId: Option[Long], campaignId: Option[Long], campaignName: Option[String])

object Campaign {
  implicit val encoder: Encoder[Campaign] = deriveEncoder
  implicit val decoder: Decoder[Campaign] = deriveDecoder
}
