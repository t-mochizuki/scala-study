package example

import io.circe.generic.semiauto._
import io.circe.{Decoder, Encoder}

case class Page(total: Int, values: Option[Seq[Value]])

object Page {
  implicit val encoder: Encoder[Page] = deriveEncoder
  implicit val decoder: Decoder[Page] = deriveDecoder
}
