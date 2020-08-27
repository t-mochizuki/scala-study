package resolve_based_auth.context

import com.softwaremill.session.{SessionManager, SessionResult}
import resolve_based_auth.repository.NumberRepo
import resolve_based_auth.routing.AuthorisationException
import resolve_based_auth.Session

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
