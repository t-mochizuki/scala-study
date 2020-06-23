package example.rest_api.boundary.db

import example.rest_api.entity.PersonEntity

import scalikejdbc._

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

  def findById(id: Int)(implicit session: DBSession): Option[PersonEntity] =
    withSQL {
      select.from(this as p)
        .where.eq(p.id, id)
    }.map(apply).single.apply()
}
