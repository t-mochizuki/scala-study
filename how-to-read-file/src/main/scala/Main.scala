import scala.io.Source

object Main extends App {
  println(Source.fromResource("me.gql").mkString)
}
