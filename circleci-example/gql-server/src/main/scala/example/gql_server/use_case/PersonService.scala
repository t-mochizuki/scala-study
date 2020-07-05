package example.gql_server.use_case

import example.gql_server.boundary.db.PersonDao
import example.gql_server.entity.PersonEntity
import scalikejdbc.DBSession

class PersonService(personDao: PersonDao) {
  def create(person: PersonEntity)(implicit session: DBSession): Int =
    personDao.create(person)

  def update(person: PersonEntity)(implicit session: DBSession): Int =
    personDao.update(person)

  def delete(id: Int)(implicit session: DBSession): Boolean =
    personDao.delete(id)

  def findList()(implicit session: DBSession): Seq[PersonEntity] =
    personDao.findList()

  def findById(id: Int)(implicit session: DBSession): Option[PersonEntity] =
    personDao.findById(id)
}
