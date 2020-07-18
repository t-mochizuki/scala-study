package example

import org.mockito.Mockito._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.diagrams.Diagrams

class FooSpec extends AnyFlatSpec with Diagrams {

  "hello" should "be null if the receiver is mock" in {
    val mockFoo = mock(classOf[Foo])
    when(mockFoo.name).thenReturn("bar")
    assert(mockFoo.name === "bar")
    assert(mockFoo.hello === null)
  }

  it should "be greeting if the receiver is spy" in {
    val spyFoo = spy(new Foo)
    when(spyFoo.name).thenReturn("bar")
    assert(spyFoo.name === "bar")
    assert(spyFoo.hello === "Hello, bar")
  }

}
