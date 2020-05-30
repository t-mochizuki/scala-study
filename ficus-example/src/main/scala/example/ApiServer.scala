package example

import net.ceedubs.ficus.Ficus.{toFicusConfig, stringValueReader}
import net.ceedubs.ficus.readers.ValueReader

object ApiServer {
  implicit val apiServerValueReader: ValueReader[ApiServer] = ValueReader.relative { config =>
    ApiServer(
      host = config.as[String]("host"),
      port = config.getInt("port")
    )
  }
}

case class ApiServer(host: String, port: Int)
