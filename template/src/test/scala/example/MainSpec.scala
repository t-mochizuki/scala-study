package example

import org.scalatest.{DiagrammedAssertions, FlatSpec}

class MainSpec extends FlatSpec with DiagrammedAssertions {
  "hello" should "return \"Hello, world.\"" in {
    assert(Main.hello === "Hello, world.")
  }
}
