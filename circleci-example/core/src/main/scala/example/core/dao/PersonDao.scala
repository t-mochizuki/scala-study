package example.core.dao

import java.time.ZonedDateTime

import scalikejdbc._

final case class PersonEntity(id: Int, name: String, createdAt: ZonedDateTime)

class PersonDao() extends SQLSyntaxSupport[PersonEntity] {
  override val tableName = "persons"

  val p = this.syntax("p")

  def apply(rs: WrappedResultSet) = autoConstruct(rs, p.resultName)

  def create(person: PersonEntity)(implicit session: DBSession): Int =
    applyUpdate {
      insertInto(this).values(
        person.id,
        person.name,
        person.createdAt)
    }

  def update(person: PersonEntity)(implicit session: DBSession): Int =
    applyUpdate {
      QueryDSL.update(this as p).set(
        p.id -> person.id,
        p.name -> person.name,
        p.createdAt -> person.createdAt)
    }

  def delete(id: Int)(implicit session: DBSession): Boolean =
    applyExecute {
      QueryDSL.delete.from(this as p).where.eq(p.id, id)
    }

  def findList(limit: Int, page: Int)(implicit session: DBSession): Seq[PersonEntity] =
    withSQL {
      select.from(this as p)
        .limit(limit)
        .offset(page)
    }.map(apply).list.apply()
}
