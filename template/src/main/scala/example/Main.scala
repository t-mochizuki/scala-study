package example

import dao.PersonDao
import logging.Logger
import scalikejdbc._
import scalikejdbc.config.DBs

object Main extends Logger {
  def hello = "Hello, world."

  def main(args: Array[String]): Unit = {
    @SuppressWarnings(Array("org.wartremover.warts.Null"))
    val foo = null

    @SuppressWarnings(Array("org.wartremover.warts.Var"))
    var bar = 42

    DBs.loadGlobalSettings()

    DBs.setup('default)

    DB readOnly { implicit session =>
      PersonDao.findList()
    }

    println(hello)
  }
}
