package api_client

import io.circe.{Decoder, Encoder, HCursor, Json}

sealed abstract class MediaServiceUserStatus {
  val value: String
}

object MediaServiceUserStatus {
  case object ACTIVE extends MediaServiceUserStatus {
    val value = "ACTIVE"
  }
  case object PAUSED extends MediaServiceUserStatus {
    val value = "PAUSED"
  }
  case object UNKNOWN extends MediaServiceUserStatus {
    val value = "UNKNOWN"
  }

  def valueOf(value: String): MediaServiceUserStatus = value match {
    case "ACTIVE" => ACTIVE
    case "PAUSED" => PAUSED
    case otherwise@_ => UNKNOWN
  }

  implicit val encoder: Encoder[MediaServiceUserStatus] = new Encoder[MediaServiceUserStatus] {
    final def apply(a: MediaServiceUserStatus): Json = Json.fromString(a.value)
  }
  implicit val decoder: Decoder[MediaServiceUserStatus] = new Decoder[MediaServiceUserStatus] {
    final def apply(c: HCursor): Decoder.Result[MediaServiceUserStatus] =
      for (value <- c.as[String]) yield valueOf(value)
  }
}
