package example.gql_server.repository

import example.rest_api.boundary.db.PersonDao
import example.rest_api.entity.PersonEntity

import scalikejdbc.DB

class PersonRepo(personDao: PersonDao) {

  def person(id: Int): Option[PersonEntity] =
    DB.readOnly { implicit session =>
      personDao.findById(id)
    }

  def persons: Seq[PersonEntity] =
    DB.readOnly { implicit session =>
      personDao.findList(10, 0)
    }

  def addPerson(person: PersonEntity): Int =
    DB.localTx { implicit session =>
      personDao.create(person)
    }

  def updatePerson(person: PersonEntity): Int =
    DB.localTx { implicit session =>
      personDao.update(person)
    }

  def deletePerson(id: Int): Boolean =
    DB.localTx { implicit session =>
      personDao.delete(id)
    }

}