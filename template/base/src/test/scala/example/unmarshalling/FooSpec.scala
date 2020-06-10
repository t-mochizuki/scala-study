package example.unmarshalling

import akka.actor.ActorSystem
import akka.testkit.TestKit
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.unmarshalling.Unmarshal
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
import io.circe.generic.auto._
import org.scalatest.{BeforeAndAfterAll, DiagrammedAssertions, FlatSpecLike}

object FooSpec {
  final case class Foo(bar: String)
}

final class FooSpec
    extends TestKit(ActorSystem())
    with FlatSpecLike
    with DiagrammedAssertions
    with BeforeAndAfterAll {
  import FooSpec._

  implicit val ec = system.dispatcher

  override def afterAll: Unit = {
    TestKit.shutdownActorSystem(system)
  }

  val entity = HttpEntity(ContentTypes.`application/json`, "{\"bar\":\"baz\"}")

  "Unmarshal" should "be succeeded" in {
    Unmarshal(entity).to[Foo].map(x => assert(x === "{\"bar\":\"baz\"}"))
  }
}
