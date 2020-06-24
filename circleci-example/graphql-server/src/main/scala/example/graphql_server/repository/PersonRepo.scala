package example.graphql_server.repository

import java.time.{ZoneId, ZonedDateTime}
import example.rest_api.entity.PersonEntity

class PersonRepo {

  val zonedDateTime = ZonedDateTime.of(2020, 6, 8, 0, 0, 0, 0, ZoneId.systemDefault)

  private val people = (
    PersonEntity(1, "manabu", zonedDateTime) ::
      PersonEntity(2, "keiko", zonedDateTime.minusDays(123)) ::
      Nil
  )

  def person(id: Int): Option[PersonEntity] =
    people.find(_.id == id)

  def persons: List[PersonEntity] = people

}
