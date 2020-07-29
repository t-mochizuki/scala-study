package example

import com.softwaremill.session.{SessionSerializer, SingleValueSessionSerializer}
import io.circe.generic.semiauto._
import io.circe.{Decoder, Encoder}

import scala.util.Try

case class Session(id: String)

object Session {
  implicit def serializer: SessionSerializer[Session, String] =
    new SingleValueSessionSerializer(_.id, (un: String) => Try { Session(un) })
  implicit val encoder: Encoder[Session] = deriveEncoder
  implicit val decoder: Decoder[Session] = deriveDecoder
}
