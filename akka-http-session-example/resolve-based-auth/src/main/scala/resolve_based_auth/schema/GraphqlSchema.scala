package resolve_based_auth.schema

import resolve_based_auth.context.UserContext
import sangria.schema._

@SuppressWarnings(Array("org.wartremover.warts.Any"))
trait GraphqlSchema {

  val QueryType = ObjectType(
    "Query",
    fields[UserContext, Unit](
      Field(
        "numbers",
        ListType(IntType),
        resolve = c =>
          c.ctx.authorized {
            c.ctx.numbers
          }
      )
    )
  )

  val schema = Schema(QueryType)

}
