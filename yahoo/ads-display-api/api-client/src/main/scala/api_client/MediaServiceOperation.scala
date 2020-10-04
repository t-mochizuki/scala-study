package api_client

import io.circe.generic.semiauto._
import io.circe.{Decoder, Encoder}

case class MediaServiceOperation(
    accountId: Long,
    operand: Seq[MediaServiceRecord]
)

object MediaServiceOperation {
  implicit val encoder: Encoder[MediaServiceOperation] = deriveEncoder
  implicit val decoder: Decoder[MediaServiceOperation] = deriveDecoder
}
