package example

import com.redis.RedisClient
import io.circe.parser.parse

object Main extends App {
  val r = new RedisClient("localhost", 6379)

  val x = Store.encoder(Store(Session("manabu", "212121")))

  val b = r.set("key", x)

  val id = r
    .get[String]("key")
    .flatMap(parse(_).toOption)
    .flatMap(Store.decoder.decodeJson(_).toOption)
    .map(_.session)
    .map(_.id)
    .getOrElse("")

  println(s"id=$id")
}
