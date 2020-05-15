package example

import sangria.execution._
import sangria.macros._
import sangria.macros.derive._
import sangria.marshalling.circe._
import sangria.schema._
import scala.concurrent.{Await, Future}
import scala.concurrent.duration.Duration
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}

object Main extends App {

  implicit val PictureType =
    deriveObjectType[Unit, Picture](
      ObjectTypeDescription("The product picture"),
      DocumentField("url", "Picture CDN URL"))

  val IdentifiableType = InterfaceType(
    "Identifiable",
    "Entity that can be identified",
    fields[Unit, Identifiable](
      Field(
        "id",
        StringType,
        resolve = v => v.value.id)))

  val ProductType =
    deriveObjectType[Unit, Product](
      Interfaces(IdentifiableType),
      IncludeMethods("picture"))

  val Id = Argument(
    "id",
    StringType)

  val QueryType = ObjectType(
    "Query",
    fields[ProductRepo, Unit](
      Field(
        "product",
        OptionType(ProductType),
        description = Some("Returns a product with specific `id`."),
        arguments = Id :: Nil,
        resolve = c => c.ctx.product(c arg Id)),
      Field(
        "products",
        ListType(ProductType),
        description = Some("Returns a list of all available products."),
        resolve = c => c.ctx.products)))

  val schema = Schema(QueryType)

  val query =
    graphql"""
      query MyProduct {
        product(id: "2") {
          name
          description

          picture(size: 500) {
            width, height, url
          }
        }

        products {
          name
        }
      }
    """

  val result =
    Executor.execute(schema, query, new ProductRepo)

  Await.ready(result, Duration.Inf)

  result.value.get match {
    case Success(json) => println(json)
    case Failure(e) => println(e.getMessage())
  }
}
