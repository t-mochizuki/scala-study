package example.gql_server.schema

import example.gql_server.schema.PersonSchema
import sangria.schema.Schema

trait GraphQLSchema extends PersonSchema {

  val schema = Schema(PersonQueryType, Some(PersonMutationType))

}
