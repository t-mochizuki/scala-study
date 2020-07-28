package example

import com.softwaremill.session.{RefreshTokenData, RefreshTokenLookupResult, RefreshTokenStorage}

import scala.collection.mutable
import scala.concurrent.Future
import scala.concurrent.duration.Duration

@SuppressWarnings(
  Array("org.wartremover.warts.NonUnitStatements")
)
trait MyRefreshTokenStorage[T] extends RefreshTokenStorage[T] {
  case class Store(session: T, tokenHash: String, expires: Long)
  private val _store = mutable.Map[String, Store]()

  def store: Map[String, Store] = _store.toMap

  override def lookup(selector: String) = {
    Future.successful {
      val r = _store
        .get(selector)
        .map(s => RefreshTokenLookupResult[T](s.tokenHash, s.expires, () => s.session))
      log(s"Looking up token for selector: $selector, found: ${r.isDefined.toString}")
      r
    }
  }

  override def store(data: RefreshTokenData[T]) = {
    log(
      s"Storing token for selector: ${data.selector}, user: ${data.forSession.toString}, " +
        s"expires: ${data.expires.toString}, now: ${System.currentTimeMillis().toString}"
    )
    Future.successful(
      _store.put(data.selector, Store(data.forSession, data.tokenHash, data.expires))
    )
  }

  override def remove(selector: String) = {
    log(s"Removing token for selector: $selector")
    Future.successful(_store.remove(selector))
  }

  override def schedule[S](after: Duration)(op: => Future[S]) = {
    log("Running scheduled operation immediately")
    op
    Future.successful(())
  }

  def log(msg: String): Unit
}
