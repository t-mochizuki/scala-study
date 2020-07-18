package example

import org.mockito.Mockito._
import org.mockito.exceptions.base.MockitoException
import org.scalatest.matchers.should.Matchers.the
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.diagrams.Diagrams

class BazSpec extends AnyFlatSpec with Diagrams {

  "mock" should "throw an exception" in {
    the [MockitoException] thrownBy mock(classOf[Baz])
  }

  "spy" should "throw an exception" in {
    the [MockitoException] thrownBy spy(new Baz)
  }

}
