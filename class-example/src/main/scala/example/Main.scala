package example

import io.circe._
import io.circe.generic.auto._
import io.circe.parser._
import io.circe.syntax._

object Main extends App {
  val condition =
    Condition(
      Seq(
        Condition(
          Seq(
            SearchCondition("name", Equals, "nana"),
            Or,
            SearchCondition("weight", GreaterThan, "48"))),
        Or,
        Condition(
          Seq(
            SearchCondition("name", Equals, "taro"),
            Or,
            SearchCondition("height", GreaterThan, "167")))))

  val json = condition.asJson

  println(json)
}
