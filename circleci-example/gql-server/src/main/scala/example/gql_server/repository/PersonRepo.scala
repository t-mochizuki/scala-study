package example.gql_server.repository

import example.rest_server.boundary.db.PersonDao
import example.rest_server.entity.PersonEntity

import scalikejdbc.DB

class PersonRepo(personDao: PersonDao) {

  def person(id: Int): Option[PersonEntity] =
    DB.readOnly { implicit session =>
      personDao.findById(id)
    }

  def persons(limit: Int, offset: Int): Seq[PersonEntity] =
    DB.readOnly { implicit session =>
      personDao.findList(limit, offset)
    }

  def create(person: PersonEntity): Int =
    DB.localTx { implicit session =>
      personDao.create(person)
    }

  def update(person: PersonEntity): Int =
    DB.localTx { implicit session =>
      personDao.update(person)
    }

  def delete(id: Int): Boolean =
    DB.localTx { implicit session =>
      personDao.delete(id)
    }

}
