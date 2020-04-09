package example

object Main extends App {
  val user = new User

  val point = new Point(2, 3)

  println(s"point.x=${point.x}")

  val tree =
    Branch(
      Branch(
        Leaf(12),
        Branch(
          Leaf(3),
          Leaf(4))),
      Leaf(8))

  println(tree)
}
