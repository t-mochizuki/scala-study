package example

import io.circe.generic.semiauto._
import io.circe.syntax._
import io.circe.{Decoder, Encoder, Json}

case class Response(rid: String, status: Int, error: String)

object Response {
  implicit val encoder: Encoder[Response] = new Encoder[Response] {
    final def apply(a: Response): Json = Json.obj(
      ("rid", Json.fromString(a.rid)),
      ("status", Json.fromInt(a.status)),
      ("error", a.error.asJson.dropNullValues))
  }
  implicit val decoder: Decoder[Response] = deriveDecoder
}
