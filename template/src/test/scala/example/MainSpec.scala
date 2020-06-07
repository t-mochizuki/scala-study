package example

import org.scalatest.{DiagrammedAssertions, FlatSpec}

class MainSpec extends FlatSpec with DiagrammedAssertions {
  "main" should "be succeeded" in {
    Main.main(Array.empty)
  }

  "hello" should "return \"Hello, world.\"" in {
    assert(Main.hello === "Hello, world!")
  }
}
