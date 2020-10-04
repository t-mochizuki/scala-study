package api_client

import io.circe.generic.semiauto._
import io.circe.{Decoder, Encoder}

case class MediaServicePage(
  totalNumEntries: Int,
  values: Option[Seq[Option[MediaServiceValue]]])

object MediaServicePage {
  implicit val encoder: Encoder[MediaServicePage] = deriveEncoder
  implicit val decoder: Decoder[MediaServicePage] = deriveDecoder
}
