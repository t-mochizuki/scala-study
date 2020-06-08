package example.dao

import java.time.{ZonedDateTime, ZoneId}
import org.scalatest.{fixture, DiagrammedAssertions}
import scalikejdbc.scalatest.AutoRollback
import scalikejdbc.{applyUpdate, autoNamedValues, insert, DB, NamedDB}
import scalikejdbc.config.DBs
import scalikejdbc.interpolation.SQLSyntax
import scalikejdbc.DBSession

class PersonDaoSpec
    extends fixture.FlatSpec
    with DiagrammedAssertions
    with AutoRollback
    with settings.DBSettings {
  override def db: DB = NamedDB('test).toDB

  val zonedDateTime = ZonedDateTime.of(2020, 6, 8, 0, 0, 0, 0, ZoneId.systemDefault)

  val persion = PersonEntity("manabu", 26, zonedDateTime)

  override def fixture(implicit session: DBSession): Unit = {
    applyUpdate(insert.into(PersonDao).namedValues(autoNamedValues(persion, PersonDao.column)))
  }

  "findList" should "return a list of person entity" in { implicit session =>
    assert(PersonDao.findList() === Seq(persion))
  }
}
