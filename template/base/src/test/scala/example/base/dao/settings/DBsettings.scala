package example.base.dao.settings

import scalikejdbc._, config._

trait DBSettings {
  DBSettings.initialize()
}

object DBSettings {

  private var isInitialized = false

  def initialize(): Unit =
    this.synchronized {
      if (isInitialized) return
      DBs.setupAll()
      isInitialized = true
    }

}
