package example

import akka.actor.ActorSystem
import akka.http.scaladsl.model.headers._
import akka.http.scaladsl.model.{MediaTypes, HttpHeader, HttpMethods, HttpRequest, Uri}
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
    val uri = args(0)
    val authorization = args(1)

    DBs.loadGlobalSettings()
    DBs.setup(Symbol("default"))
    DB readOnly { implicit session =>
      PersonDao.findList()
    }

    implicit val system: ActorSystem = ActorSystem()
    implicit val executionContext: ExecutionContext = system.dispatcher

    val acceptHeader: HttpHeader = Accept(MediaTypes.`application/json`)
    val authorizationHeader: HttpHeader = RawHeader("authorization", authorization)
    val headers: Seq[HttpHeader] = Seq(acceptHeader, authorizationHeader)
    val httpRequest = HttpRequest(method = HttpMethods.GET, uri = Uri(uri), headers = headers)

    Client
      .send(httpRequest)
      .onComplete[Any] {
        case Success(res) => {
          println(res)
          println(res.entity.discardBytes())
          system.terminate()
        }
        case Failure(e) => logger.error(e.getMessage())
      }

    println(hello)
  }
}
