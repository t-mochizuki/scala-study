package example

import com.redis.RedisClient
import com.redis.serialization.Parse.Implicits.parseByteArray

object Main extends App {
  val r = new RedisClient("localhost", 6379)

  val x = Serializer.serialize(Store(Session("manabu")))

  val b = r.set("key", x)

  val id = r
    .get[Array[Byte]]("key")
    .map(Serializer.deserialize[Store])
    .map(_.session)
    .map(_.id)
    .getOrElse("")

  println(s"id=$id")
}
