package example

import io.circe.{ Encoder, Json }
import io.circe.syntax._

sealed case class WhereOperator(value: String)
object WhereOperator {
  implicit val encoder: Encoder[WhereOperator] = Encoder.instance {
    case a @ WhereOperator(_) => Json.fromString(a.value)
  }
}

object Equals extends WhereOperator("Equals")
object GreaterThan extends WhereOperator("GreaterThan")
