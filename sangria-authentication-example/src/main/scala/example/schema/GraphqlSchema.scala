package example.schema

import example.context.UserContext
import sangria.schema._

trait GraphqlSchema {

  @SuppressWarnings(Array("org.wartremover.warts.Any"))
  val QueryType = ObjectType(
    "Query",
    fields[UserContext, Unit](
      Field(
        "numbers",
        ListType(IntType),
        resolve = c => c.ctx.numbers)))

  val IdArg = Argument("id", StringType)
  val PasswordArg = Argument("password", StringType)

  @SuppressWarnings(Array("org.wartremover.warts.Any"))
  val MutationType = ObjectType(
    "Mutation",
    fields[UserContext, Unit](
      Field(
        "login",
        StringType,
        arguments = IdArg :: PasswordArg :: Nil,
        resolve = c => c.ctx.login(c.arg(IdArg), c.arg(PasswordArg))),
      Field(
        "logout",
        StringType,
        arguments = Nil,
        resolve = c => c.ctx.logout())))

  val schema = Schema(QueryType, Some(MutationType))

}
