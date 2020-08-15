package redis_client

import com.redis.RedisClient
import io.circe.parser.parse

object Main extends App {
  val redisClient = new RedisClient("localhost", 6379)

  val x = Store.encoder(Store(Session("manabu", "212121")))

  val b = redisClient.set("key", x)

  val id = redisClient
    .get[String]("key")
    .flatMap(parse(_).toOption)
    .flatMap(Store.decoder.decodeJson(_).toOption)
    .map(_.session)
    .map(_.id)
    .getOrElse("")

  println(s"id=$id")
}
