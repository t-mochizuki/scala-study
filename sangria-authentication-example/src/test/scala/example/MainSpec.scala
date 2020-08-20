package example

import akka.http.scaladsl.model.headers.RawHeader
import akka.http.scaladsl.model.{
  ContentTypes,
  FormData,
  HttpEntity,
  HttpMethods,
  HttpRequest,
  StatusCodes
}
import akka.http.scaladsl.testkit.ScalatestRouteTest
import akka.http.scaladsl.unmarshalling.Unmarshal
import com.softwaremill.session.{SessionConfig, SessionManager}
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
import io.circe.Json
import org.scalatest.diagrams.Diagrams
import org.scalatest.flatspec.AnyFlatSpec

import scala.concurrent.Await
import scala.concurrent.duration.Duration

class MainSpec extends AnyFlatSpec with Diagrams with ScalatestRouteTest {

  behavior of "Main"

  val sessionConfig = SessionConfig.fromConfig()

  implicit val sessionManager = new SessionManager[Session](sessionConfig)

  "login" should ("be Unauthorized if id wrong") in {
    val request = Post("/login", FormData("id" -> "id", "password" -> "admin"))
    request ~> Main.createRoutes() ~> check {
      assert(status === StatusCodes.Unauthorized)
    }
  }

  it should ("be OK if id and password are correct") in {
    val request = Post("/login", FormData("id" -> "admin", "password" -> "admin"))
    request ~> Main.createRoutes() ~> check {
      assert(status === StatusCodes.OK)
    }
  }

  "logout" should ("be OK") in {
    Post("/logout") ~> Main.createRoutes() ~> check {
      assert(status === StatusCodes.OK)
    }
  }

  "graphql" should ("be OK") in {
    val request = Post("/login", FormData("id" -> "admin", "password" -> "admin"))
    request ~> Main.createRoutes() ~> check {
      val httpEntity = HttpEntity(ContentTypes.`application/json`, """{"query":"{numbers}"}""")
      val request = HttpRequest(method = HttpMethods.POST, uri = "/graphql").withEntity(httpEntity)
      val requestHeader = RawHeader(
        "Authorization",
        header(sessionConfig.sessionHeaderConfig.sendToClientHeaderName).get.value
      )
      request ~> requestHeader ~> Main.createRoutes() ~> check {
        val f = Unmarshal(responseEntity).to[Json].map { json =>
          val v = json.hcursor
            .downField("data")
            .downField("numbers")
            .downN(0)
            .focus
            .flatMap(_.as[Int].toOption)
          assert(v === Some(2))
        }
        Await.result(f, Duration.Inf)
        assert(status === StatusCodes.OK)
      }
    }
  }

  it should ("be authentication exception") in {
    val httpEntity = HttpEntity(ContentTypes.`application/json`, """{"query":"{numbers}"}""")
    val request = HttpRequest(method = HttpMethods.POST, uri = "/graphql").withEntity(httpEntity)
    request ~> Main.createRoutes() ~> check {
      val f = Unmarshal(responseEntity).to[Json].map { json =>
        val v = json.hcursor
          .downField("errors")
          .downN(0)
          .downField("message")
          .focus
          .flatMap(_.as[String].toOption)
        assert(v === Some("Access token is wrong"))
      }
      Await.result(f, Duration.Inf)
      assert(status === StatusCodes.OK)
    }
  }

  it should "be OK without login" in {
    val httpEntity =
      HttpEntity(ContentTypes.`application/json`, """{"query":"{ __schema { types { name } } }"}""")
    val request = HttpRequest(method = HttpMethods.POST, uri = "/graphql").withEntity(httpEntity)
    request ~> Main.createRoutes() ~> check {
      val f = Unmarshal(responseEntity).to[Json].map { json =>
        val v = json.hcursor
          .downField("data")
          .downField("__schema")
          .downField("types")
          .downN(0)
          .downField("name")
          .focus
          .flatMap(_.as[String].toOption)
        assert(v === Some("Query"))
      }
      Await.result(f, Duration.Inf)
      assert(status === StatusCodes.OK)
    }
  }
}
