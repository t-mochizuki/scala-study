package example

object Main extends App {
  val condition =
    Condition(Seq(SearchCondition("name", Equals, "nana")))

  println(condition)
}
