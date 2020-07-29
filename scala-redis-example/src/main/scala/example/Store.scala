package example

import io.circe.generic.semiauto._
import io.circe.{Decoder, Encoder}

final case class Store(session: Session)

object Store {
  implicit val encoder: Encoder[Store] = deriveEncoder
  implicit val decoder: Decoder[Store] = deriveDecoder
}
