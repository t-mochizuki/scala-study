package example

object Main {
  def main(args: Array[String]): Unit = {
    @SuppressWarnings(Array("org.wartremover.warts.Null"))
    val foo = null

    @SuppressWarnings(Array("org.wartremover.warts.Var"))
    var bar = 42

    println("Hello, world.")
  }
}
