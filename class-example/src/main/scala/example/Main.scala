package example

object Main extends App {
  val condition =
    Condition(
      Seq(
        SearchCondition("name", Equals, "nana"),
        Or,
        SearchCondition("name", Equals, "taro")))

  println(condition)
}
