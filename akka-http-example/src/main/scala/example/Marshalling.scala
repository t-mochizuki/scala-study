package example

import akka.Done
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives
import akka.stream.ActorMaterializer
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport

import scala.io.StdIn

import scala.concurrent.{ ExecutionContext, Future }

object Marshalling {
  var orders: List[Item] = Nil

  // domain model
  final case class Item(name: String, id: Long)
  final case class Order(items: List[Item])

  // (fake) async database query api
  def fetchItem(itemId: Long)(implicit ec: ExecutionContext): Future[Option[Item]] = Future {
    orders.find(o => o.id == itemId)
  }
  def saveOrder(order: Order)(implicit ec: ExecutionContext): Future[Done] = {
    orders = order match {
      case Order(items) => items ::: orders
      case _            => orders
    }
    Future { Done }
  }

  private def route(implicit sys: ActorSystem, ec: ExecutionContext) = {
    import Directives._
    import FailFastCirceSupport._
    import io.circe.generic.auto._

    concat(
      get {
        pathPrefix("item" / LongNumber) { id =>
          // there might be no item for a given id
          val maybeItem: Future[Option[Item]] = fetchItem(id)

          onSuccess(maybeItem) {
            case Some(item) => complete(item)
            case None       => complete(StatusCodes.NotFound)
          }
        }
      },
      post {
        path("create-order") {
          entity(as[Order]) { order =>
            val saved: Future[Done] = saveOrder(order)
            onComplete(saved) { done =>
              complete("order created")
            }
          }
        }
      }
    )
  }

  def main(args: Array[String]): Unit = {
    implicit val system = ActorSystem("my-system")
    implicit val materializer = ActorMaterializer()
    // needed for the future flatMap/onComplete in the end
    implicit val executionContext = system.dispatcher


    val bindingFuture = Http().bindAndHandle(route, "localhost", 8080)
    println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")

    StdIn.readLine()
    bindingFuture
      .flatMap(_.unbind()) // trigger unbinding from the port
      .onComplete(_ => system.terminate()) // and shutdown when done
  }
}
