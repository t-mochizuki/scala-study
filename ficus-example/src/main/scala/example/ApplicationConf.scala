package example

import com.typesafe.config.{Config, ConfigFactory}
import net.ceedubs.ficus.Ficus._
import net.ceedubs.ficus.readers.ArbitraryTypeReader._

case class ApiServer(host: String, port: Int)

object ApplicationConf {

  private[this] val config = ConfigFactory.defaultOverrides
    .withFallback(ConfigFactory.load("default"))
    .withFallback(ConfigFactory.load())

  lazy val apiServer = config.as[ApiServer]("api-server")

  def baz(): Int = {
    println("BAZ!")
    26
  }

  lazy val foo = baz()
}
