package example

import io.circe.Json
import io.circe.parser._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.diagrams.Diagrams

class PageSpec extends AnyFlatSpec with Diagrams {

  behavior of "Page"

  it should "be OK" in {
    val response = Page(0, None)
    assert(response == Page.decoder(Page.encoder(response).hcursor).getOrElse(response.copy(total = 1)))

    val json = """
{
  "total":0,
  "values":null
}
"""
    assert(Page.encoder(response) == parse(json).getOrElse(Json.Null))
    assert(Page.decoder(parse(json).getOrElse(Json.Null).hcursor) == Right(Page(0, None)))
  }

  it should "be OK even if values is not Nil" in {
    val response = Page(1, Some(Seq(Value("name", Seq(Error("code", "message"))))))
    assert(Right(response) == Page.decoder(Page.encoder(response).hcursor))
  }

}
