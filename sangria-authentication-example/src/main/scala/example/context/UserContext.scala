package example.context

import com.softwaremill.session.{SessionManager, SessionResult}
import example.repository.NumberRepo
import example.routing.AuthenticationException
import example.Session

case class UserContext(token: Option[String], numberRepo: NumberRepo)(implicit sessionManager: SessionManager[Session]) {

  @SuppressWarnings(Array("org.wartremover.warts.Throw"))
  def numbers(): List[Int] =
    token
      .flatMap { v =>
        sessionManager.clientSessionManager.decode(v) match {
          case s: SessionResult.Decoded[Session] => Some(s.session.id)
          case _ => None
        }
      }
      .map(_ => numberRepo.numbers)
      .getOrElse(throw new AuthenticationException("Access token is not found"))
}
