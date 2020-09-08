package example

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.diagrams.Diagrams

class PageSpec extends AnyFlatSpec with Diagrams {

  behavior of "Page"

  it should "be OK" in {
    val response = Page(0, Nil)
    assert(response == Page.decoder(Page.encoder(response).hcursor).getOrElse(response.copy(total = 1)))
  }

  it should "be OK even if there is null" in {
    val response = Page(0, null)
    assert(response == Page.decoder(Page.encoder(response).hcursor).getOrElse(response.copy(total = 1)))
  }

  it should "be OK even if values is not Nil" in {
    val response = Page(1, Seq(Value("name", Seq(Error("code", "message")))))
    assert(response == Page.decoder(Page.encoder(response).hcursor).getOrElse(response.copy(total = 2)))
  }

}
