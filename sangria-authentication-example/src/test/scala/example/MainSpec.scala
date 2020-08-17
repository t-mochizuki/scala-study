package example

import akka.http.scaladsl.model.headers.RawHeader
import akka.http.scaladsl.model.{ContentTypes, FormData, HttpEntity, HttpMethods, HttpRequest, StatusCodes}
import akka.http.scaladsl.testkit.ScalatestRouteTest
import com.softwaremill.session.{SessionConfig, SessionManager}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.diagrams.Diagrams

class MainSpec extends AnyFlatSpec with Diagrams with ScalatestRouteTest {

  behavior of "Main"

  val sessionConfig = SessionConfig.fromConfig()

  implicit val sessionManager = new SessionManager[Session](sessionConfig)

  "login" should ("be Unauthorized if id wrong") in {
    Post("/login", FormData("id" -> "id", "password" -> "admin")) ~>
    Main.createRoutes() ~> check {
      assert(status === StatusCodes.Unauthorized)
    }
  }

  it should ("be OK if id and password are correct") in {
    Post("/login", FormData("id" -> "admin", "password" -> "admin")) ~>
    Main.createRoutes() ~> check {
      assert(status === StatusCodes.OK)
    }
  }

  "logout" should ("be OK") in {
    Post("/logout") ~>
    Main.createRoutes() ~> check {
      assert(status === StatusCodes.OK)
    }
  }

  "graphql" should ("be OK") in {
    Post("/login", FormData("id" -> "admin", "password" -> "admin")) ~>
    Main.createRoutes() ~> check {
      HttpRequest(
        method = HttpMethods.POST,
        uri = "/graphql")
      .withEntity(
        HttpEntity(
          ContentTypes.`application/json`,
          """{"query":"{numbers}"}""")) ~>
      RawHeader("Authorization", header("Set-Authorization").get.value) ~>
      Main.createRoutes() ~> check {
        assert(responseEntity.httpEntity.contentLengthOption === Some(28))
        assert(status === StatusCodes.OK)
      }
    }
  }

  it should ("be authentication exception") in {
    HttpRequest(
      method = HttpMethods.POST,
      uri = "/graphql")
    .withEntity(
      HttpEntity(
        ContentTypes.`application/json`,
        """{"query":"{numbers}"}""")) ~>
    RawHeader("Authorization", "") ~>
    Main.createRoutes() ~> check {
      assert(responseEntity.httpEntity.contentLengthOption === Some(119))
      assert(status === StatusCodes.OK)
    }
  }


  it should "be OK without login" in {
    HttpRequest(
      method = HttpMethods.POST,
      uri = "/graphql")
    .withEntity(
      HttpEntity(
        ContentTypes.`application/json`,
        """{"query":"{ __schema { types { name } } }"}""")) ~>
    Main.createRoutes() ~> check {
      assert(responseEntity.httpEntity.contentLengthOption === Some(282))
      assert(status === StatusCodes.OK)
    }
  }
}
