import Fib.fib
import org.scalatest.{FunSpec, MustMatchers}

class FibSpec extends FunSpec with MustMatchers {
  describe("Fib") {
    it("fib") {
      fib(0) mustBe 1
      fib(1) mustBe 1
      fib(2) mustBe 2
      fib(3) mustBe 3
      fib(4) mustBe 5
      fib(5) mustBe 8
      fib(6) mustBe 13
      fib(7) mustBe 21
    }
  }
}
