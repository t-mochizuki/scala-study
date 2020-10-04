package api_client

import io.circe.generic.semiauto._
import io.circe.{Decoder, Encoder}

case class MediaServiceSelector(
  accountId: Long,
  startIndex: Int,
  numberResults: Int
)

object MediaServiceSelector {
  implicit val encoder: Encoder[MediaServiceSelector] = deriveEncoder
  implicit val decoder: Decoder[MediaServiceSelector] = deriveDecoder
}
