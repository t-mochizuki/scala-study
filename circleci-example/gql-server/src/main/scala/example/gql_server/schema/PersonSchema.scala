package example.gql_server.schema

import example.rest_api.entity.PersonEntity
import sangria.schema._

trait PersonSchema {

  @SuppressWarnings(Array("org.wartremover.warts.Any"))
  val PersonType = ObjectType(
    "Person",
    "The person",
    fields[Unit, PersonEntity](
      Field("id", IntType, resolve = _.value.id),
      Field("name", StringType, resolve = _.value.name),
      Field("createdAt", ZonedDateTimeType, resolve = _.value.createdAt)
    )
  )

}
