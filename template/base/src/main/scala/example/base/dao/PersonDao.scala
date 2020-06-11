package example.base.dao

import example.base.logging.Logger
import io.circe.{Decoder, Encoder}
import io.circe.generic.semiauto._
import java.time.ZonedDateTime
import scalikejdbc._

final case class PersonEntity(name: String, age: Int, createdAt: ZonedDateTime)

object PersonEntity {
  implicit val encoder: Encoder[PersonEntity] = deriveEncoder
  implicit val decoder: Decoder[PersonEntity] = deriveDecoder
}

object PersonDao extends SQLSyntaxSupport[PersonEntity] with Logger {
  override val tableName = "persons"
  def apply(rs: WrappedResultSet) =
    PersonEntity(rs.string("name"), rs.int("age"), rs.zonedDateTime("created_at"))
  def findList()(implicit session: DBSession): Seq[PersonEntity] =
    sql"select * from persons".map(rs => apply(rs)).list.apply()
}
