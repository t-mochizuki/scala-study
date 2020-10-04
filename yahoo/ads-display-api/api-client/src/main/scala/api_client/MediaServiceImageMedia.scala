package api_client

import io.circe.generic.semiauto._
import io.circe.{Decoder, Encoder}

case class MediaServiceImageMedia(
  data: Option[Array[Byte]]
)

object MediaServiceImageMedia {
  implicit val encoder: Encoder[MediaServiceImageMedia] = deriveEncoder
  implicit val decoder: Decoder[MediaServiceImageMedia] = deriveDecoder
}
