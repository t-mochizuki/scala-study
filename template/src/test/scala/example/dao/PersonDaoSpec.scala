package example.dao

import org.scalatest.{fixture, DiagrammedAssertions}
import scalikejdbc.scalatest.AutoRollback
import scalikejdbc.{DB, NamedDB}
import scalikejdbc.config.DBs

class PersonDaoSpec extends fixture.FlatSpec with DiagrammedAssertions with AutoRollback {
  DBs.setup('test)

  override def db(): DB = {
    NamedDB('test).toDB()
  }

  "findList" should "return List()" in { implicit session =>
    assert(PersonDao.findList() === List())
  }
}
