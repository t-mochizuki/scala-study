package example.unmarshalling

import java.time.{ZonedDateTime, ZoneId}

import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.testkit.ScalatestRouteTest
import akka.http.scaladsl.unmarshalling.Unmarshal
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
import example.dao.PersonEntity
import io.circe.generic.auto._
import org.scalatest.{DiagrammedAssertions, FlatSpec}

class PersonEntitySpec extends FlatSpec with DiagrammedAssertions with ScalatestRouteTest {
  val zonedDateTime = ZonedDateTime.of(2020, 6, 8, 0, 0, 0, 0, ZoneId.systemDefault)

  val mother = PersonEntity("keiko", 24, zonedDateTime)

  val input = PersonEntity.encoder(mother).toString

  val entity = HttpEntity(ContentTypes.`application/json`, input)

  "Unmarshal" should "be succeeded" in {
    Unmarshal(entity).to[PersonEntity].map(x => assert(x === input))
  }
}
