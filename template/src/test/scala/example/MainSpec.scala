package example

import org.scalatest.{FlatSpec, MustMatchers}

class MainSpec extends FlatSpec with MustMatchers {
  "main" should "be succeeded" in {
    Main.main(Array.empty)
  }

  "hello" should "returns \"Hello, world.\"" in {
    Main.hello mustBe "Hello, world."
  }
}
