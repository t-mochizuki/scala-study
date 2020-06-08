package example.dao

import example.logging.Logger
import java.time.ZonedDateTime
import scalikejdbc._

case class PersonEntity(name: String, age: Int, createdAt: ZonedDateTime)

object PersonDao extends SQLSyntaxSupport[PersonEntity] with Logger {
  override val tableName                                         = "persons"
  def apply(rs: WrappedResultSet)                                =
    PersonEntity(rs.string("name"), rs.int("age"), rs.zonedDateTime("created_at"))
  def findList()(implicit session: DBSession): Seq[PersonEntity] =
    sql"select * from persons".map(rs => apply(rs)).list.apply()
}
