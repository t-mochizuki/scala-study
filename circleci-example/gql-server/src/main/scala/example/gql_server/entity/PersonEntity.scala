package example.gql_server.entity

import java.time.ZonedDateTime

import io.circe.generic.semiauto._
import io.circe.{Decoder, Encoder}

import scalikejdbc.{autoConstruct, ResultName, WrappedResultSet}

final case class PersonEntity(id: Int, name: String, createdAt: ZonedDateTime)

object PersonEntity {
  implicit val encoder: Encoder[PersonEntity] = deriveEncoder
  implicit val decoder: Decoder[PersonEntity] = deriveDecoder

  def apply(rn: ResultName[PersonEntity])(rs: WrappedResultSet) = autoConstruct(rs, rn)
}
