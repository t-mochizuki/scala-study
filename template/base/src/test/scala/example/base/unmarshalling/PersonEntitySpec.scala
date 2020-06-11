package example.base.unmarshalling

import java.time.{ZonedDateTime, ZoneId}

import akka.actor.ActorSystem
import akka.testkit.TestKit
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.unmarshalling.Unmarshal
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
import example.base.dao.PersonEntity
import io.circe.generic.auto._
import org.scalatest.{BeforeAndAfterAll, DiagrammedAssertions, FlatSpecLike}

final class PersonEntitySpec
    extends TestKit(ActorSystem())
    with FlatSpecLike
    with DiagrammedAssertions
    with BeforeAndAfterAll {

  implicit val ec = system.dispatcher

  override def afterAll: Unit = {
    TestKit.shutdownActorSystem(system)
  }

  val zonedDateTime = ZonedDateTime.of(2020, 6, 8, 0, 0, 0, 0, ZoneId.systemDefault)

  val mother = PersonEntity("keiko", 24, zonedDateTime)

  val input = PersonEntity.encoder(mother).toString

  val entity = HttpEntity(ContentTypes.`application/json`, input)

  "Unmarshal" should "be succeeded" in {
    Unmarshal(entity).to[PersonEntity].map(x => assert(x === input))
  }
}
