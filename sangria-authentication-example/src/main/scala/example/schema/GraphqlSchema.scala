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

  val schema = Schema(QueryType)

}
