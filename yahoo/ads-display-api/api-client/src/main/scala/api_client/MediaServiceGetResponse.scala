package api_client

import io.circe.generic.semiauto._
import io.circe.{Decoder, Encoder}

case class MediaServiceGetResponse(rid: String, rval: Option[MediaServicePage], errors: Option[Seq[Option[Error]]])

object MediaServiceGetResponse {
  implicit val encoder: Encoder[MediaServiceGetResponse] = deriveEncoder
  implicit val decoder: Decoder[MediaServiceGetResponse] = deriveDecoder
}
