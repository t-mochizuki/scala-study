package example.rest_server.boundary.db

import java.time.{ZoneId, ZonedDateTime}

import example.core.settings.DBSettings
import example.rest_server.entity.PersonEntity
import org.scalatest.{fixture, DiagrammedAssertions}
import scalikejdbc.scalatest.AutoRollback
import scalikejdbc.{applyUpdate, autoNamedValues, insert}
import scalikejdbc.DBSession

final class PersonDaoSpec
    extends fixture.FlatSpec
    with DiagrammedAssertions
    with AutoRollback
    with DBSettings {

  behavior of "PersonDao"

  val zonedDateTime = ZonedDateTime.of(2020, 6, 8, 0, 0, 0, 0, ZoneId.systemDefault)

  val person = PersonEntity(1, "manabu", zonedDateTime)

  val personDao = new PersonDao()

  override def fixture(implicit session: DBSession): Unit = {
    applyUpdate(insert.into(personDao).namedValues(autoNamedValues(person, personDao.column)))
  }

  "create" should "insert a record of person entity" in { implicit session =>
    val keiko = PersonEntity(2, "keiko", zonedDateTime)
    personDao.create(keiko)
    assert(personDao.findList(10, 0).map(_.copy(id = 1)) === Seq(person, keiko).map(_.copy(id = 1)))
  }

  "update" should "update a record of person entity" in { implicit session =>
    val keiko = PersonEntity(1, "keiko", zonedDateTime.plusYears(1))
    personDao.update(keiko)
    assert(personDao.findList(10, 0) === Seq(keiko))
  }

  "delete" should "delete a record of person entity" in { implicit session =>
    personDao.delete(person.id)
    assert(personDao.findList(10, 0) === Nil)
  }

  "findList" should "return a list of person entity" in { implicit session =>
    assert(personDao.findList(10, 0) === Seq(person))
  }
}
