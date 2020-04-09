package example

sealed trait ConditionT
case class SearchCondition(key: String, op: WhereOperator, value: String) extends ConditionT
case class Condition(condition: Seq[ConditionT]) extends ConditionT
