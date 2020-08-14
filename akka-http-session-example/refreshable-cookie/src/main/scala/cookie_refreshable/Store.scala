package refreshable_cookie

import io.circe.generic.semiauto._
import io.circe.{Decoder, Encoder}

case class Store(session: Session, tokenHash: String, expires: Long)

object Store {
  implicit val encoder: Encoder[Store] = deriveEncoder
  implicit val decoder: Decoder[Store] = deriveDecoder
}
