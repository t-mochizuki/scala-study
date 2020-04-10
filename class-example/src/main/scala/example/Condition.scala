package example

import io.circe.{ Encoder, Json }
import io.circe.syntax._

sealed abstract class ConditionT
object ConditionT {
  implicit val encoder: Encoder[ConditionT] = Encoder.instance {
    case a @ Operator(_) => a.asJson
    case a @ SearchCondition(_, _, _) => a.asJson
    case a @ Condition(_) => a.asJson
  }
}

case class Condition(condition: Seq[ConditionT]) extends ConditionT
object Condition {
  implicit val encoder: Encoder[Condition] = new Encoder[Condition] {
    final def apply(a: Condition): Json = Json.arr(
      a.condition.map(_.asJson): _*
    )
  }
}

case class SearchCondition(key: String, op: WhereOperator, value: String) extends ConditionT
object SearchCondition {
  implicit val encoder: Encoder[SearchCondition] = new Encoder[SearchCondition] {
    final def apply(a: SearchCondition): Json = Json.obj(
      ("key", Json.fromString(a.key)),
      ("op", a.op.asJson),
      ("value", Json.fromString(a.value))
    )
  }
}

sealed case class Operator(val value: String) extends ConditionT
object Operator {
  implicit val encoder: Encoder[Operator] = Encoder.instance {
    case a @ Operator(_) => Json.fromString(a.value)
  }
}

object And extends Operator("And")
object Or extends Operator("Or")
