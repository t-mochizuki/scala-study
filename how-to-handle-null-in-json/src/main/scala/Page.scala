package example

import io.circe._
import io.circe.syntax._

case class Page(total: Int, values: Seq[Value])

object Page {
  implicit val encoder: Encoder[Page] = new Encoder[Page] {
    final def apply(a: Page): Json = Json.obj(
      ("total", Json.fromInt(a.total)),
      ("values", if (a.values == null) Json.Null else a.values.asJson.dropNullValues))
  }
  implicit val decoder: Decoder[Page] = new Decoder[Page] {
    final def apply(c: HCursor): Decoder.Result[Page] =
      for {
        total <- c.downField("total").as[Int]
      } yield {
        if (c.downField("values").succeeded) Page(total, c.downField("values").as[Seq[Value]].getOrElse(null))
        else Page(total, null)
      }
  }
}
