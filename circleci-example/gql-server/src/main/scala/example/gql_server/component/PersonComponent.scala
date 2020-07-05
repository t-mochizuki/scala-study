package example.gql_server.component

import example.gql_server.boundary.db.PersonDao
import example.gql_server.handler.PersonHandler
import example.gql_server.use_case.PersonService

trait PersonComponent {
  lazy val personDaoImpl = new PersonDao()
  lazy val personServiceImpl = new PersonService(personDaoImpl)
  lazy val personHandlerImpl = new PersonHandler(personServiceImpl)
}
