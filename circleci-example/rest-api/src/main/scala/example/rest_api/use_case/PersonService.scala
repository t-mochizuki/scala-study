package example.rest_api.use_case

import example.rest_api.boundary.db.PersonDao
import example.rest_api.entity.PersonEntity
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
}
