package example.base.dao.settings

import scalikejdbc._, config._

trait DBSettings {
  DBSettings.initialize()
}

object DBSettings {

  @SuppressWarnings(Array("org.wartremover.warts.Var"))
  private var isInitialized = false

  @SuppressWarnings(Array("org.wartremover.warts.NonUnitStatements", "org.wartremover.warts.Return"))
  def initialize(): Unit =
    this.synchronized {
      if (isInitialized) return
      DBs.loadGlobalSettings()
      DBs.readJDBCSettings(Symbol("default"))
      ConnectionPool.singleton(
        DBs.config.getString("db.default.url"),
        DBs.config.getString("db.default.user"),
        DBs.config.getString("db.default.password")
      )
      isInitialized = true
    }

}
