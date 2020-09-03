package api_client

import io.circe.generic.semiauto._
import io.circe.{Decoder, Encoder}

case class ErrorDetail(requestKey: String, requestValue: String)

object ErrorDetail {
  implicit val encoder: Encoder[ErrorDetail] = deriveEncoder
  implicit val decoder: Decoder[ErrorDetail] = deriveDecoder
}
