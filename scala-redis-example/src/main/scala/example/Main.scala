package example

import io.circe.Json
import io.circe.parser.parse
import com.redis.RedisClient

object Main extends App {
  val r = new RedisClient("localhost", 6379)

  val x = Store.encoder(Store(Session("manabu"))).toString

  val b = r.set("key", x)

  val id = r
    .get[String]("key")
    .map(parse(_).getOrElse(Json.Null))
    .map(_.hcursor)
    .map(Store.decoder(_).getOrElse(Store(Session("keiko"))))
    .map(_.session)
    .map(_.id)
    .getOrElse("")

  println(s"id=$id")
}
