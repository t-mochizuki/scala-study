package example.unmarshalling

import java.time.{ZonedDateTime, ZoneId}

import akka.actor.ActorSystem
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import example.dao.PersonEntity
import org.scalatest.{DiagrammedAssertions, FlatSpec}
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
import io.circe.generic.auto._

class PersonEntitySpec extends FlatSpec with DiagrammedAssertions {
  implicit val system = ActorSystem()
  implicit val ec = system.dispatcher

  val zonedDateTime = ZonedDateTime.of(2020, 6, 8, 0, 0, 0, 0, ZoneId.systemDefault)

  val mother = PersonEntity("keiko", 24, zonedDateTime)

  val input = PersonEntity.encoder(mother).toString

  val entity = HttpEntity(ContentTypes.`application/json`, input)

  "Unmarshal" should "be succeeded" in {
    Unmarshal(entity).to[PersonEntity].map(x => assert(x === input))
  }
}
