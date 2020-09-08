package example

import io.circe.generic.semiauto._
import io.circe.{Decoder, Encoder, Json}

case class Error(code: String, message: String)

object Error {
  implicit val encoder: Encoder[Error] = deriveEncoder
  implicit val decoder: Decoder[Error] = deriveDecoder
}
