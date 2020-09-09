package example

import io.circe.generic.semiauto._
import io.circe.{Decoder, Encoder}

case class Value(name: String, errors: Seq[Error])

object Value {
  implicit val encoder: Encoder[Value] = deriveEncoder
  implicit val decoder: Decoder[Value] = deriveDecoder
}
