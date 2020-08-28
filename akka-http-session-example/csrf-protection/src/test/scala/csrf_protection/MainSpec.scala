package csrf_protection

import akka.http.scaladsl.model.headers.{`Set-Cookie`, Cookie}
import akka.http.scaladsl.model.{FormData, StatusCodes}
import akka.http.scaladsl.testkit.ScalatestRouteTest
import com.softwaremill.session.{SessionConfig, SessionManager}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.diagrams.Diagrams

class MainSpec extends AnyFlatSpec with Diagrams with ScalatestRouteTest {

  behavior of "Main"

  val sessionConfig = SessionConfig.fromConfig()

  implicit val sessionManager = new SessionManager[Session](sessionConfig)

  "login" should ("be OK if id and password are correct") in {
    Get("/csrf-token") ~> Main.createRoutes() ~> check {
      Post("/login", FormData("id" -> "admin", "password" -> "admin")) ~>
      Cookie(header[`Set-Cookie`].get.cookie.name -> header[`Set-Cookie`].get.cookie.value) ~>
      addHeader(sessionConfig.csrfSubmittedName, header[`Set-Cookie`].get.cookie.value) ~>
      Main.createRoutes() ~> check {
        assert(status === StatusCodes.OK)
      }
    }
  }

  "hello" should ("be OK") in {
    Get("/csrf-token") ~> Main.createRoutes() ~> check {
      Post("/login", FormData("id" -> "admin", "password" -> "admin")) ~>
      Cookie(header[`Set-Cookie`].get.cookie.name -> header[`Set-Cookie`].get.cookie.value) ~>
      addHeader(sessionConfig.csrfSubmittedName, header[`Set-Cookie`].get.cookie.value) ~>
      Main.createRoutes() ~> check {
        Get("/hello") ~>
        Cookie(sessionConfig.sessionCookieConfig.name -> header[`Set-Cookie`].get.cookie.value) ~>
        Main.createRoutes() ~> check {
          assert(status === StatusCodes.OK)
        }
      }
    }
  }

  "logout" should ("be OK") in {
    Get("/csrf-token") ~> Main.createRoutes() ~> check {
      Post("/logout", FormData("id" -> "admin", "password" -> "admin")) ~>
      Cookie(header[`Set-Cookie`].get.cookie.name -> header[`Set-Cookie`].get.cookie.value) ~>
      addHeader(sessionConfig.csrfSubmittedName, header[`Set-Cookie`].get.cookie.value) ~>
      Main.createRoutes() ~> check {
        assert(status === StatusCodes.OK)
      }
    }
  }

}
