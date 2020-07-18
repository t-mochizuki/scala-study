package example

import org.mockito.Mockito._
import org.mockito.exceptions.base.MockitoException
import org.scalatest.matchers.should.Matchers.the
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.diagrams.Diagrams

class MockSpec extends AnyFlatSpec with Diagrams {

  behavior of "mock"

  "hello" should "be null if the receiver is mock" in {
    val mockFoo = mock(classOf[Foo])
    when(mockFoo.name).thenReturn("bar")
    assert(mockFoo.name === "bar")
    assert(mockFoo.hello === null)
  }

  "mock" should "throw an exception if the receiver is final class" in {
    the [MockitoException] thrownBy mock(classOf[Baz])
  }

  it should "throw an exception if the receiver is object" in {
    the [MockitoException] thrownBy mock(Bar.getClass)
  }

}
