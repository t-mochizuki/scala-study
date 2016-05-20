import org.json4s._

object Main {
  case class Foo(name: String, age: Long)
  def main(args: Array[String]) {
    implicit val format = DefaultFormats
    Extraction.decompose(Foo("one", 1))
  }
}
