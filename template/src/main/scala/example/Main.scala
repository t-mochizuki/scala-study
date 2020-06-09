package example

import akka.actor.ActorSystem
import akka.http.scaladsl.model.{MediaTypes, HttpMethods, HttpRequest, Uri}
import akka.http.scaladsl.model.headers
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
    DBs.loadGlobalSettings()

    DBs.setup(Symbol("default"))

    DB readOnly { implicit session =>
      PersonDao.findList()
    }

    implicit val system: ActorSystem = ActorSystem()

    implicit val executionContext: ExecutionContext = system.dispatcher

    val acceptHeader = headers.Accept(MediaTypes.`application/json`)

    val httpRequest = HttpRequest(
      method = HttpMethods.GET,
      uri = Uri(args(0)),
      headers = Seq(acceptHeader))

    Client
      .send(httpRequest)
      .onComplete[Any] {
        case Success(res) => {
          println(res)
          println(res.entity.discardBytes())
          system.terminate()
        }
        case Failure(e)   => logger.error(e.getMessage())
      }

    println(hello)
  }
}
