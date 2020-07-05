package example.gql_server.handler

import example.gql_server.entity.PersonEntity
import example.gql_server.use_case.PersonService
import scalikejdbc.DBSession

class PersonHandler(personService: PersonService) {
  def create(person: PersonEntity)(implicit session: DBSession): Int =
    personService.create(person)

  def update(person: PersonEntity)(implicit session: DBSession): Int =
    personService.update(person)

  def delete(id: Int)(implicit session: DBSession): Boolean =
    personService.delete(id)

  def findList()(implicit session: DBSession): Seq[PersonEntity] =
    personService.findList()

  def findById(id: Int)(implicit session: DBSession): Option[PersonEntity] =
    personService.findById(id)
}
