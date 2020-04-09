package example

sealed trait ConditionT
case class SearchCondition(key: String, op: WhereOperator, value: String) extends ConditionT
case class Condition(condition: Seq[ConditionT]) extends ConditionT

sealed abstract class Operator(value: String) extends ConditionT
case object And extends Operator("And")
case object Or extends Operator("Or")
