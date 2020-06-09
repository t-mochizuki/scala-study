package example.unmarshalling

import akka.actor.ActorSystem
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import org.scalatest.{DiagrammedAssertions, FlatSpec}
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
import io.circe.generic.auto._

object FooSpec {
  final case class Foo(bar: String)
}

final class FooSpec extends FlatSpec with DiagrammedAssertions {
  import FooSpec._

  implicit val system = ActorSystem()
  implicit val ec = system.dispatcher

  val entity = HttpEntity(ContentTypes.`application/json`, "{\"bar\":\"baz\"}")

  "Unmarshal" should "be succeeded" in {
    Unmarshal(entity).to[Foo].map(x => assert(x === "{\"bar\":\"baz\"}"))
  }
}
