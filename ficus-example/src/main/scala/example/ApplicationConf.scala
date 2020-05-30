package example

import com.typesafe.config.{Config, ConfigFactory}
import net.ceedubs.ficus.Ficus.{toFicusConfig}

object ApplicationConf {

  private[this] val config = ConfigFactory.defaultOverrides
    .withFallback(ConfigFactory.load("default"))
    .withFallback(ConfigFactory.load())

  lazy val apiServer = config.as[ApiServer]("api-server")(ApiServer.apiServerValueReader)

  def baz(): Int = {
    println("BAZ!")
    26
  }

  lazy val foo = baz()
}
