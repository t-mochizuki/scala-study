package example.gql_server.schema

import example.gql_server.context.UserContext
import example.gql_server.entity.PersonEntity
import scalikejdbc.DB
import sangria.marshalling.circe._
import sangria.relay.{Connection, ConnectionArgs, ConnectionDefinition}
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
  val personField = Field(
    "person",
    OptionType(PersonType),
    arguments = Id :: Nil,
    resolve = (c: Context[UserContext, Unit]) =>
      DB.readOnly { implicit session =>
        c.ctx.personHandler.findById(c arg Id)
      }
  )

  @SuppressWarnings(Array("org.wartremover.warts.Any"))
  val ConnectionDefinition(_, personConnection) =
    Connection.definition[UserContext, Connection, PersonEntity]("Persons", PersonType)

  @SuppressWarnings(Array("org.wartremover.warts.Any"))
  val personsField = Field(
    "persons",
    OptionType(personConnection),
    arguments = Connection.Args.All,
    resolve = (c: Context[UserContext, Unit]) =>
      DB.readOnly { implicit session =>
        Connection.connectionFromSeq(
          c.ctx.personHandler.findList(),
          ConnectionArgs(c)
        )
      }
  )

  val PersonArg = Argument("person", PersonInputType)

  @SuppressWarnings(Array("org.wartremover.warts.Any"))
  val createPersonField = Field(
    "createPerson",
    OptionType(IntType),
    arguments = PersonArg :: Nil,
    resolve = (c: Context[UserContext, Unit]) =>
      DB.localTx { implicit session =>
        c.ctx.personHandler.create(c arg PersonArg)
      }
  )

  @SuppressWarnings(Array("org.wartremover.warts.Any"))
  val updatePersonField = Field(
    "updatePerson",
    OptionType(IntType),
    arguments = PersonArg :: Nil,
    resolve = (c: Context[UserContext, Unit]) =>
      DB.localTx { implicit session =>
        c.ctx.personHandler.update(c arg PersonArg)
      }
  )

  @SuppressWarnings(Array("org.wartremover.warts.Any"))
  val deletePersonField = Field(
    "deletePerson",
    OptionType(BooleanType),
    arguments = Id :: Nil,
    resolve = (c: Context[UserContext, Unit]) =>
      DB.localTx { implicit session =>
        c.ctx.personHandler.delete(c arg Id)
      }
  )

}
