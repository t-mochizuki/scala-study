package example.context

import com.softwaremill.session.{SessionManager, SessionResult}
import example.repository.NumberRepo
import example.routing.AuthorisationException
import example.Session

case class UserContext(token: Option[String], numberRepo: NumberRepo)(implicit
    sessionManager: SessionManager[Session]
) {

  @SuppressWarnings(Array("org.wartremover.warts.Throw"))
  def authorized[T](fn: => T): T =
    token
      .flatMap { v =>
        sessionManager.clientSessionManager.decode(v) match {
          case s: SessionResult.Decoded[Session] => Some(s.session.id)
          case _ => None
        }
      }
      .map(_ => fn)
      .getOrElse(throw new AuthorisationException("Access token is wrong"))

  def numbers(): List[Int] = numberRepo.numbers
}
