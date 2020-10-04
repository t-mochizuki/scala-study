package api_client

import io.circe.generic.semiauto._
import io.circe.{Decoder, Encoder}

case class MediaServiceReturnValue(
  values: Option[Seq[Option[MediaServiceValue]]])

object MediaServiceReturnValue {
  implicit val encoder: Encoder[MediaServiceReturnValue] = deriveEncoder
  implicit val decoder: Decoder[MediaServiceReturnValue] = deriveDecoder
}
