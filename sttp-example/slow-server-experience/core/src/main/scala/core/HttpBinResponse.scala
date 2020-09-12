package core

import io.circe.Decoder
import io.circe.generic.semiauto.deriveDecoder

case class HttpBinResponse(origin: String, headers: Map[String, String])

object HttpBinResponse {
  implicit val decoder : Decoder[HttpBinResponse] = deriveDecoder
}
