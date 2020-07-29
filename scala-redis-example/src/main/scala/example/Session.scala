package example

import io.circe.generic.semiauto._
import io.circe.{Decoder, Encoder}

case class Session(id: String)

object Session {
  implicit val encoder: Encoder[Session] = deriveEncoder
  implicit val decoder: Decoder[Session] = deriveDecoder
}
