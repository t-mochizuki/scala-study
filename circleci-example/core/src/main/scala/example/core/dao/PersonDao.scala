package example.core.dao

import java.time.ZonedDateTime

import scalikejdbc._

final case class PersonEntity(id: Int, name: String, createdAt: ZonedDateTime)

object PersonDao extends SQLSyntaxSupport[PersonEntity] {
  override val tableName = "persons"

  val p = this.syntax("p")

  def apply(rs: WrappedResultSet) = autoConstruct(rs, p.resultName)

  def findList(limit: Int, page: Int)(implicit session: DBSession): Seq[PersonEntity] =
    withSQL {
      select.from(this as p)
        .limit(limit)
        .offset(page)
    }.map(apply).list.apply()
}
