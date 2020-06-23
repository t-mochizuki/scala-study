package example.rest_api.boundary.db

import example.rest_api.entity.PersonEntity

import scalikejdbc._

class PersonDao() extends SQLSyntaxSupport[PersonEntity] {
  override val tableName = "persons"

  val p = this.syntax("p")

  def apply(rn: ResultName[PersonEntity])(rs: WrappedResultSet) = autoConstruct(rs, rn)

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
        p.name -> person.name,
        p.createdAt -> person.createdAt).where.eq(p.id, person.id)
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
    }.map(apply(p.resultName)).list.apply()

  def findById(id: Int)(implicit session: DBSession): Option[PersonEntity] =
    withSQL {
      select.from(this as p)
        .where.eq(p.id, id)
    }.map(apply(p.resultName)).single.apply()
}
