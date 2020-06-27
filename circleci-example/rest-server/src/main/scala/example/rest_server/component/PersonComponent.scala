package example.rest_server.component

import example.rest_server.boundary.db.PersonDao
import example.rest_server.handler.PersonHandler
import example.rest_server.use_case.PersonService

trait PersonComponent {
  lazy val personDao = new PersonDao()
  lazy val personService = new PersonService(personDao)
  lazy val personHandler = new PersonHandler(personService)
}
