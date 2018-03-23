package example

import org.scalatest.{FunSpec, MustMatchers}
import org.mockito.Mockito._
import org.scalatest.mockito.MockitoSugar

class ExampleSpec extends FunSpec with MustMatchers with MockitoSugar {
  describe("Example") {
    it("mock") {
      val mockSeq = mock[Seq[Long]]
      when(mockSeq.take(3)).thenReturn(Seq(3L, 4L))
      mockSeq.take(3) mustBe Seq(3, 4)
    }
  }
}
