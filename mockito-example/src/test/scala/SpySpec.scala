package example

import org.mockito.Mockito._
import org.mockito.exceptions.base.MockitoException
import org.scalatest.matchers.should.Matchers.the
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.diagrams.Diagrams

class SpySpec extends AnyFlatSpec with Diagrams {

  behavior of "spy"

  "hello" should "be greeting if the receiver is spy" in {
    val spyFoo = spy(new Foo)
    when(spyFoo.name).thenReturn("bar")
    assert(spyFoo.name === "bar")
    assert(spyFoo.hello === "Hello, bar")
  }

  "spy" should "throw an exception if the receiver is final class" in {
    the [MockitoException] thrownBy spy(new Baz)
  }

  it should "throw an exception if the receiver is object" in {
    the [MockitoException] thrownBy spy(Bar)
  }

}
