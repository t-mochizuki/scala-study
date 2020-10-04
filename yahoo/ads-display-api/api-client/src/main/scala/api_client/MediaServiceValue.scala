package api_client

import io.circe.generic.semiauto._
import io.circe.{Decoder, Encoder}

case class MediaServiceValue(
  mediaRecord: Option[MediaServiceRecord],
  operationSucceeded: Option[Boolean],
  errors: Option[Seq[Option[Error]]])

object MediaServiceValue {
  implicit val encoder: Encoder[MediaServiceValue] = deriveEncoder
  implicit val decoder: Decoder[MediaServiceValue] = deriveDecoder
}
