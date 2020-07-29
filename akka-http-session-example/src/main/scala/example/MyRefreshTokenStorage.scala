package example

import com.softwaremill.session.{RefreshTokenData, RefreshTokenLookupResult, RefreshTokenStorage}
import com.redis.RedisClient
import com.redis.serialization.Parse.Implicits.parseByteArray

import scala.concurrent.Future
import scala.concurrent.duration.Duration

@SuppressWarnings(
  Array("org.wartremover.warts.NonUnitStatements")
)
trait MyRefreshTokenStorage extends RefreshTokenStorage[Session] {
  private val _store = new RedisClient("localhost", 6379)

  override def lookup(selector: String): Future[Option[RefreshTokenLookupResult[Session]]] = {
    Future.successful {
      val r = _store
        .get[Array[Byte]](selector)
        .map(Serializer.deserialize[Store])
        .map(s => RefreshTokenLookupResult[Session](s.tokenHash, s.expires, () => s.session))
      log(s"Looking up token for selector: $selector, found: ${r.isDefined.toString}")
      r
    }
  }

  override def store(data: RefreshTokenData[Session]): Future[Unit] = {
    log(
      s"Storing token for selector: ${data.selector}, user: ${data.forSession.toString}, " +
        s"expires: ${data.expires.toString}, now: ${System.currentTimeMillis().toString}"
    )
    Future.successful(
      _store.set(data.selector, Serializer.serialize(Store(data.forSession, data.tokenHash, data.expires)))
    )
  }

  override def remove(selector: String): Future[Unit] = {
    log(s"Removing token for selector: $selector")
    Future.successful(_store.del(selector))
  }

  override def schedule[S](after: Duration)(op: => Future[S]): Unit = {
    log("Running scheduled operation immediately")
    op
    Future.successful(())
  }

  def log(msg: String): Unit
}
