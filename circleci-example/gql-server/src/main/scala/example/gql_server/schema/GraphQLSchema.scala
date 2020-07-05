package example.gql_server.schema

import example.gql_server.context.UserContext
import example.gql_server.schema.PersonSchema
import sangria.schema._

trait GraphQLSchema extends PersonSchema {

  @SuppressWarnings(Array("org.wartremover.warts.Any"))
  val QueryType = ObjectType(
    "Query",
    fields[UserContext, Unit](
      personField,
      personsField
    )
  )

  @SuppressWarnings(Array("org.wartremover.warts.Any"))
  val MutationType = ObjectType(
    "Mutation",
    fields[UserContext, Unit](
      createPersonField,
      updatePersonField,
      deletePersonField
    )
  )

  val schema = Schema(QueryType, Some(MutationType))

}
