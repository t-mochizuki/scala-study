package example.gql_server.boundary.db

import example.gql_server.entity.PersonEntity

import scalikejdbc._

class PersonDao() extends SQLSyntaxSupport[PersonEntity] {
  override val tableName = "persons"

  val p = this.syntax("p")

  def create(person: PersonEntity)(implicit session: DBSession): Int =
    applyUpdate {
      insertInto(this)
        .columns(this.column.name, this.column.createdAt)
        .values(person.name, person.createdAt)
    }

  def update(person: PersonEntity)(implicit session: DBSession): Int =
    applyUpdate {
      QueryDSL
        .update(this as p)
        .set(p.name -> person.name, p.createdAt -> person.createdAt)
        .where
        .eq(p.id, person.id)
    }

  def delete(id: Int)(implicit session: DBSession): Boolean =
    applyExecute {
      QueryDSL.delete.from(this as p).where.eq(p.id, id)
    }

  def findList()(implicit session: DBSession): Seq[PersonEntity] =
    withSQL {
      select
        .from(this as p)
    }.map(PersonEntity.apply(p.resultName)).list.apply()

  def findById(id: Int)(implicit session: DBSession): Option[PersonEntity] =
    withSQL {
      select.from(this as p).where.eq(p.id, id)
    }.map(PersonEntity.apply(p.resultName)).single.apply()
}
