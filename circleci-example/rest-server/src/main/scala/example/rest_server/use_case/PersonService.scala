package example.rest_server.use_case

import example.rest_server.boundary.db.PersonDao
import example.rest_server.entity.PersonEntity
import scalikejdbc.DBSession

class PersonService(personDao: PersonDao) {
  def create(person: PersonEntity)(implicit session: DBSession): Int =
    personDao.create(person)

  def update(person: PersonEntity)(implicit session: DBSession): Int =
    personDao.update(person)

  def delete(id: Int)(implicit session: DBSession): Boolean =
    personDao.delete(id)

  def findList(limit: Int, offset: Int)(implicit session: DBSession): Seq[PersonEntity] =
    personDao.findList(limit, offset)

  def findById(id: Int)(implicit session: DBSession): Option[PersonEntity] =
    personDao.findById(id)
}
