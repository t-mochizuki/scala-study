package example

import org.json4s._
import org.json4s.jackson.JsonMethods._
import org.json4s.JsonDSL._

object Main extends App {
  val json = List(1, 2, 3)

  println(compact(render(json)))
}
