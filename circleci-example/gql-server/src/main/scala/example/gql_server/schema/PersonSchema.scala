package example.gql_server.schema

import example.rest_api.entity.PersonEntity
import example.gql_server.repository.PersonRepo
import sangria.schema._
import sangria.marshalling.circe._

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

  val PersonArg = Argument("person", PersonInputType)

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
      Field("persons", ListType(PersonType), resolve = _.ctx.persons)
    )
  )

  @SuppressWarnings(Array("org.wartremover.warts.Any"))
  val PersonMutationType = ObjectType(
    "PersonMutation",
    fields[PersonRepo, Unit](
      Field(
        "addPerson",
        OptionType(IntType),
        arguments = PersonArg :: Nil,
        resolve = c => c.ctx.addPerson(c arg PersonArg)
      ),
      Field(
        "updatePerson",
        OptionType(IntType),
        arguments = PersonArg :: Nil,
        resolve = c => c.ctx.updatePerson(c arg PersonArg)
      ),
      Field(
        "deletePerson",
        OptionType(BooleanType),
        arguments = Id :: Nil,
        resolve = c => c.ctx.deletePerson(c arg Id)
      )
    )
  )

}
