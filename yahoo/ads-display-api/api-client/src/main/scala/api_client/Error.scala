package api_client

import io.circe.generic.semiauto._
import io.circe.{Decoder, Encoder}

case class Error(code: String, message: String, details: Seq[ErrorDetail])

object Error {
  implicit val encoder: Encoder[Error] = deriveEncoder
  implicit val decoder: Decoder[Error] = deriveDecoder
}
