package example.base.dao.settings

import scalikejdbc._, config._

trait DBSettings {
  DBSettings.initialize()
}

object DBSettings {

  @SuppressWarnings(Array("org.wartremover.warts.NonUnitStatements", "org.wartremover.warts.Return"))
  def initialize(): Unit =
    this.synchronized {
      if (ConnectionPool.isInitialized(Symbol("default"))) return
      DBs.loadGlobalSettings()
      val JDBCSettings(url, user, password, _) = DBs.readJDBCSettings(Symbol("default"))
      val settings = ConnectionPoolSettings()
      ConnectionPool.singleton(
        url,
        user,
        password,
        settings
      )
    }

}
