package example

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

  println(condition)
}
