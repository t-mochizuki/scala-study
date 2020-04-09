package example

sealed abstract class WhereOperator(value: String)
case object Equals extends WhereOperator("Equals")
case object GreaterThan extends WhereOperator("GreaterThan")
