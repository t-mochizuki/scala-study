package example.gql_server.schema

import example.gql_server.repository.PersonRepo
import example.rest_server.entity.PersonEntity
import sangria.marshalling.circe._
import sangria.relay.{Connection, ConnectionDefinition, ConnectionArgs}
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

  val PersonInputType = InputObjectType[PersonEntity](
    "PersonInput",
    List(
      InputField("id", IntType),
      InputField("name", StringType),
      InputField("createdAt", ZonedDateTimeType)
    )
  )

  @SuppressWarnings(Array("org.wartremover.warts.Any"))
  val ConnectionDefinition(_, personConnection) =
    Connection.definition[PersonRepo, Connection, PersonEntity]("Persons", PersonType)

  @SuppressWarnings(Array("org.wartremover.warts.Any"))
  val PersonQueryType = ObjectType(
    "PersonQuery",
    fields[PersonRepo, Unit](
      Field(
        "person",
        OptionType(PersonType),
        arguments = Id :: Nil,
        resolve = c => c.ctx.person(c arg Id)
      ),
      Field(
        "persons",
        OptionType(personConnection),
        arguments = Connection.Args.All,
        resolve = c => Connection.connectionFromSeq(c.ctx.persons(10, 0), ConnectionArgs(c)))
    )
  )

  val PersonArg = Argument("person", PersonInputType)

  @SuppressWarnings(Array("org.wartremover.warts.Any"))
  val PersonMutationType = ObjectType(
    "PersonMutation",
    fields[PersonRepo, Unit](
      Field(
        "create",
        OptionType(IntType),
        arguments = PersonArg :: Nil,
        resolve = c => c.ctx.create(c arg PersonArg)
      ),
      Field(
        "update",
        OptionType(IntType),
        arguments = PersonArg :: Nil,
        resolve = c => c.ctx.update(c arg PersonArg)
      ),
      Field(
        "delete",
        OptionType(BooleanType),
        arguments = Id :: Nil,
        resolve = c => c.ctx.delete(c arg Id)
      )
    )
  )

}
