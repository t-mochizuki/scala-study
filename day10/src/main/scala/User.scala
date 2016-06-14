case class User(name: Name, age: Age)

class Name private(val value: String) extends AnyVal
object Name {
  def fromString(s: String): Name = new Name(s)
  // def fromString(s: String): Option[Name] =
  //   if (s != "") Some(new Name(s)) else None
}

class Age private(val value: Int) extends AnyVal
object Age {
  def fromInt(i: Int): Age = new Age(i)
  // def fromInt(i: Int): Option[Age] =
  //   if (i >= 18 || i <= 120)
  //     Some(new Age(i))
  //   else
  //     None
}
