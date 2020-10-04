package api_client

import io.circe.{Decoder, Encoder, HCursor, Json}

sealed abstract class MediaServiceLogoFlg {
  val value: String
}

object MediaServiceLogoFlg {
  case object TRUE extends MediaServiceLogoFlg {
    val value = "TRUE"
  }
  case object FALSE extends MediaServiceLogoFlg {
    val value = "FALSE"
  }
  case object UNKNOWN extends MediaServiceLogoFlg {
    val value = "UNKNOWN"
  }

  def valueOf(value: String): MediaServiceLogoFlg = value match {
    case "TRUE" => TRUE
    case "FALSE" => FALSE
    case otherwise@_ => UNKNOWN
  }

  implicit val encoder: Encoder[MediaServiceLogoFlg] = new Encoder[MediaServiceLogoFlg] {
    final def apply(a: MediaServiceLogoFlg): Json = Json.fromString(a.value)
  }
  implicit val decoder: Decoder[MediaServiceLogoFlg] = new Decoder[MediaServiceLogoFlg] {
    final def apply(c: HCursor): Decoder.Result[MediaServiceLogoFlg] =
      for (value <- c.as[String]) yield valueOf(value)
  }
}
