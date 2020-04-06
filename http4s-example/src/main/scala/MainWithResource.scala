import cats.effect.{ IO, IOApp, ExitCode }
import cats.implicits._
import org.http4s.server.blaze.BlazeServerBuilder

object MainWithResource extends IOApp {

  def run(args: List[String]): IO[ExitCode] =
    BlazeServerBuilder[IO]
      .bindHttp(8082, "localhost")
      .withHttpApp(Main.helloWorldService)
      .resource
      .use(_ => IO.never)
      .as(ExitCode.Success)
}
