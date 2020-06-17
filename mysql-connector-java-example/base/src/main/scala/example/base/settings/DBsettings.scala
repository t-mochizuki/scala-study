package example.base.dao.settings

import scalikejdbc._, config._

trait DBSettings {
  DBSettings.initialize()
}

object DBSettings {

  @SuppressWarnings(Array(
    "org.wartremover.warts.NonUnitStatements",
    "org.wartremover.warts.Return"))
  def initialize(): Unit =
    this.synchronized {
      if (ConnectionPool.isInitialized(Symbol("default"))) return
      DBs.loadGlobalSettings()
      val JDBCSettings(url, user, password, _) = DBs.readJDBCSettings(Symbol("default"))
      val configMap = DBs.readAsMap(Symbol("default"))
      val settings = ConnectionPoolSettings(
        initialSize = configMap.get("poolInitialSize").map(_.toInt).getOrElse(0),
        maxSize = configMap.get("poolMaxSize").map(_.toInt).getOrElse(8),
        connectionTimeoutMillis = configMap.get("poolConnectionTimeoutMillis").map(_.toLong).getOrElse(5000L),
        validationQuery = "select 1;")
      ConnectionPool.singleton(
        url,
        user,
        password,
        settings
      )
    }

}
