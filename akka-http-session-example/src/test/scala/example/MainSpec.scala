package example

import akka.http.scaladsl.model.{FormData, StatusCodes}
import akka.http.scaladsl.testkit.ScalatestRouteTest
import org.scalatest.{DiagrammedAssertions, FlatSpec}

class MainSpec
    extends FlatSpec
    with DiagrammedAssertions
    with ScalatestRouteTest {

  behavior of "Main"

  "routes" should ("be Unauthorized if id or password is wrong") in {
    Post("/login", FormData("id" -> "id", "password" -> "password")) ~> Main.routes ~> check {
      assert(status === StatusCodes.Unauthorized)
    }
  }

}
