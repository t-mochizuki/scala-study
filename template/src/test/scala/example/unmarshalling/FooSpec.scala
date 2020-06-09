package example.unmarshalling

import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.testkit.ScalatestRouteTest
import akka.http.scaladsl.unmarshalling.Unmarshal
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
import io.circe.generic.auto._
import org.scalatest.{DiagrammedAssertions, FlatSpec}

object FooSpec {
  final case class Foo(bar: String)
}

final class FooSpec extends FlatSpec with DiagrammedAssertions with ScalatestRouteTest {
  import FooSpec._

  val entity = HttpEntity(ContentTypes.`application/json`, "{\"bar\":\"baz\"}")

  "Unmarshal" should "be succeeded" in {
    Unmarshal(entity).to[Foo].map(x => assert(x === "{\"bar\":\"baz\"}"))
  }
}
