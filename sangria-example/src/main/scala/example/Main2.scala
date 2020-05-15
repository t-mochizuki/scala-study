package example

import sangria.execution._
import sangria.macros._
import sangria.marshalling.circe._
import sangria.schema._
import scala.concurrent.{Await, Future}
import scala.concurrent.duration.Duration
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}

object Main2 extends App {
  val QueryType = ObjectType(
    "Query",
    fields[NumberRepo, Unit](
      Field(
        "numbers",
        ListType(IntType),
        resolve = c => c.ctx.numbers)))

  val schema = Schema(QueryType)

  val query =
    graphql"""
      query MyNumber {
        numbers
      }
    """

  val result =
    Executor.execute(schema, query, new NumberRepo)


  Await.ready(result, Duration.Inf)

  result.value.get match {
    case Success(json) => println(json)
    case Failure(e) => println(e.getMessage())
  }
}
