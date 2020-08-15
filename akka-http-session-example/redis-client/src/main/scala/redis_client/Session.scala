package redis_client

import io.circe.generic.semiauto._
import io.circe.{Decoder, Encoder}

case class Session(id: String, password: String)

object Session {
  implicit val encoder: Encoder[Session] = deriveEncoder
  implicit val decoder: Decoder[Session] = deriveDecoder
}
