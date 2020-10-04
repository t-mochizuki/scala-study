package api_client

import io.circe.generic.semiauto._
import io.circe.{Decoder, Encoder}

case class MediaServiceRecord(
  imageMedia: MediaServiceImageMedia,
  logoFlg: MediaServiceLogoFlg,
  mediaName: String,
  mediaTitle: String,
  userStatus: MediaServiceUserStatus
)

object MediaServiceRecord {
  implicit val encoder: Encoder[MediaServiceRecord] = deriveEncoder
  implicit val decoder: Decoder[MediaServiceRecord] = deriveDecoder
}
