package example.core.dao

import java.time.{ZoneId, ZonedDateTime}
import org.scalatest.{fixture, DiagrammedAssertions}
import scalikejdbc.scalatest.AutoRollback
import scalikejdbc.{applyUpdate, autoNamedValues, insert}
import scalikejdbc.DBSession

final class PersonDaoSpec
    extends fixture.FlatSpec
    with DiagrammedAssertions
    with AutoRollback
    with settings.DBSettings {

  behavior of "PersonDao"

  val zonedDateTime = ZonedDateTime.of(2020, 6, 8, 0, 0, 0, 0, ZoneId.systemDefault)

  val person = PersonEntity(1, "manabu", zonedDateTime)

  override def fixture(implicit session: DBSession): Unit = {
    applyUpdate(insert.into(PersonDao).namedValues(autoNamedValues(person, PersonDao.column)))
  }

  "create" should "insert a record of person entity" in { implicit session =>
    val keiko = PersonEntity(2, "keiko", zonedDateTime)
    PersonDao.create(keiko)
    assert(PersonDao.findList(10, 0) === Seq(person, keiko))
  }

  "update" should "update a record of person entity" in { implicit session =>
    val keiko = PersonEntity(2, "keiko", zonedDateTime.plusYears(1))
    PersonDao.update(keiko)
    assert(PersonDao.findList(10, 0) === Seq(keiko))
  }

  "delete" should "delete a record of person entity" in { implicit session =>
    PersonDao.delete(person.id)
    assert(PersonDao.findList(10, 0) === Nil)
  }

  "findList" should "return a list of person entity" in { implicit session =>
    assert(PersonDao.findList(10, 0) === Seq(person))
  }
}
