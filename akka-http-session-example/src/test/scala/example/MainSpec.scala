package example

import akka.http.scaladsl.model.headers.{`Set-Cookie`, Cookie}
import akka.http.scaladsl.model.{FormData, StatusCodes}
import akka.http.scaladsl.testkit.ScalatestRouteTest
import com.softwaremill.session.{InMemoryRefreshTokenStorage, SessionConfig, SessionManager}
import org.scalatest.{DiagrammedAssertions, FlatSpec}

class MainSpec extends FlatSpec with DiagrammedAssertions with ScalatestRouteTest {

  behavior of "Main"

  val sessionConfig = SessionConfig.fromConfig()

  implicit val sessionManager = new SessionManager[Session](sessionConfig)

  implicit val refreshTokenStorage = new InMemoryRefreshTokenStorage[Session] {
    def log(msg: String) = println(msg)
  }

  implicit val ec = system.dispatcher

  "login" should ("be Unauthorized if id or password is wrong") in {
    Post("/login", FormData("id" -> "id", "password" -> "password")) ~> Main
      .createRoutes() ~> check {
      assert(status === StatusCodes.Unauthorized)
    }
  }

  it should ("be OK if id and password are correct") in {
    Post("/login", FormData("id" -> "admin", "password" -> "admin")) ~> Main
      .createRoutes() ~> check {
      assert(status === StatusCodes.OK)
    }
  }

  "logout" should ("be OK") in {
    Post("/logout") ~> Main.createRoutes() ~> check {
      assert(status === StatusCodes.OK)
    }
  }

  "hello" should ("be OK") in {
    Post("/login", FormData("id" -> "admin", "password" -> "admin")) ~> Main
      .createRoutes() ~> check {
      Get("/hello") ~> Cookie("_sessiondata" -> header[`Set-Cookie`].get.cookie.value) ~> Main
        .createRoutes() ~> check {
        assert(status === StatusCodes.OK)
      }
    }
  }

}
