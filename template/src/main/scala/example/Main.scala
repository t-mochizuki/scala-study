package example

import dao.PersonDao
import scalikejdbc._
import scalikejdbc.config.DBs

object Main {
  def hello = "Hello, world."

  def main(args: Array[String]): Unit = {
    @SuppressWarnings(Array("org.wartremover.warts.Null"))
    val foo = null

    @SuppressWarnings(Array("org.wartremover.warts.Var"))
    var bar = 42

    DBs.setup()

    DB readOnly { implicit session =>
      PersonDao.findList()
    }

    println(hello)
  }
}
