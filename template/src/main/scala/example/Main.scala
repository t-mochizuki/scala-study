package example

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.client.RequestBuilding.Get
import akka.http.scaladsl.model.{HttpMethods, HttpRequest, Uri}
import dao.PersonDao
import http.Client
import logging.Logger
import scalikejdbc._
import scalikejdbc.config.DBs

import scala.concurrent.ExecutionContext
import scala.util.{Failure, Success}

object Main extends Logger {
  def hello = "Hello, world."

  def main(args: Array[String]): Unit = {
    @SuppressWarnings(Array("org.wartremover.warts.Null"))
    val foo = null

    @SuppressWarnings(Array("org.wartremover.warts.Var"))
    var bar = 42

    DBs.loadGlobalSettings()

    DBs.setup(Symbol("default"))

    DB readOnly { implicit session =>
      PersonDao.findList()
    }

    implicit val system: ActorSystem = ActorSystem()

    implicit val executionContext: ExecutionContext = system.dispatcher

    val httpRequest = HttpRequest(method = HttpMethods.GET, uri = Uri("http://akka.io"))

    Client
      .send(httpRequest)
      .onComplete[Any] {
        case Success(res) => {
          println(res)
          val entity = res.entity.discardBytes()
          system.terminate()
        }
        case Failure(e)   => logger.error(e.getMessage())
      }

    println(hello)
  }
}
