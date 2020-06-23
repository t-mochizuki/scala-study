package example.rest_api.component

import example.rest_api.boundary.db.PersonDao
import example.rest_api.handler.PersonHandler
import example.rest_api.use_case.PersonService

trait PersonComponent {
  lazy val personDao = new PersonDao()
  lazy val personService = new PersonService(personDao)
  lazy val personHandler = new PersonHandler(personService)
}
