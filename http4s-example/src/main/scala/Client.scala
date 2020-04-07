import cats.effect._
import cats.implicits._
import org.http4s.Uri
import org.http4s.client.blaze._
import org.http4s.client._
import scala.concurrent.ExecutionContext.Implicits.global

object Client extends App {

  implicit val cs: ContextShift[IO] = IO.contextShift(global)
  implicit val timer: Timer[IO] = IO.timer(global)

  val greetingList = BlazeClientBuilder[IO](global).resource.use { client =>
    def hello(name: String): IO[String] = {
      val target = Uri.uri("http://localhost:8081/hello/") / name
      client.expect[String](target)
    }

    val people = Vector("Michael", "Jessica", "Ashley", "Christopher")

    people.parTraverse(hello)
  }

  val greetingsStringEffect = greetingList.map(_.mkString("\n"))

  println(greetingsStringEffect.unsafeRunSync)
}
