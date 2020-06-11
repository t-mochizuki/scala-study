package example.base.http

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpRequest, HttpResponse}

import scala.concurrent.{ExecutionContext, Future}

object Client {
  def send(
      httpRequest: HttpRequest
  )(implicit system: ActorSystem, executionContext: ExecutionContext): Future[HttpResponse] =
    for {
      httpResponse <- Http().singleRequest(httpRequest)
    } yield {
      httpResponse
    }
}
