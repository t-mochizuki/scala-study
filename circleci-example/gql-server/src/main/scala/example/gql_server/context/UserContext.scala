package example.gql_server.context

import example.gql_server.handler.PersonHandler

trait UserContext {
  def personHandler: PersonHandler
}
