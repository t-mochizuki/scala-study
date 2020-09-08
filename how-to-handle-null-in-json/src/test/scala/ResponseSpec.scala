package example

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.diagrams.Diagrams

class ResponseSpec extends AnyFlatSpec with Diagrams {

  behavior of "Response"

  it should "be OK" in {
    val response = Response("123", 404, "hello")
    assert(response == Response.decoder(Response.encoder(response).hcursor).getOrElse(response.copy(rid = "124")))
  }

  it should "be OK even if there is null" in {
    val response = Response("123", 200, null)
    assert(response == Response.decoder(Response.encoder(response).hcursor).getOrElse(response.copy(rid = "124")))
  }
}
