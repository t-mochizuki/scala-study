import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import akka.http.scaladsl.server.Directives._

object HelloAkka extends App {
  implicit val system = ActorSystem("system")
  implicit val materializer = ActorMaterializer()

  val route =
    pathSingleSlash {
      get {
        complete {
          "Hello from AkkaHttp!"
        }
      }
    }

  Http().bindAndHandle(route, "localhost", 8080)
}
