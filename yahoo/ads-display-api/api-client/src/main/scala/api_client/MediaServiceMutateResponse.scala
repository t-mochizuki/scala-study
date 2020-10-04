package api_client

import io.circe.generic.semiauto._
import io.circe.{Decoder, Encoder}

case class MediaServiceMutateResponse(
  rid: String,
  rval: Option[MediaServiceReturnValue],
  errors: Option[Seq[Option[Error]]])

object MediaServiceMutateResponse {
  implicit val encoder: Encoder[MediaServiceMutateResponse] = deriveEncoder
  implicit val decoder: Decoder[MediaServiceMutateResponse] = deriveDecoder
}
