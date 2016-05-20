import argonaut._, Argonaut._

object Main extends App {
  case class Address(street: String, number: Int, postcode: Int)

  object Address {
    implicit def AddressCodecJson: CodecJson[Address] =
      casecodec3(Address.apply, Address.unapply)("street", "number", "post_code")
  }

  case class Person(name: String, age: Int, address: Option[Address], greeting: Option[String])

  object Person {
    implicit def PersonCodecJson: CodecJson[Person] =
      casecodec4(Person.apply, Person.unapply)("name", "age", "address", "greeting")
  }

  val address = Address("Shinjuku", 1, 2)
  val parson = Person("Taro", 10, Some(address), None)
  val leftLeaf = Leaf("left leaf")
  val rightLeaf = Leaf("right leaf")
  val branch = Branch(leftLeaf, rightLeaf)

  println(address.asJson.spaces4)
  println(parson.asJson.spaces2)
  println(leftLeaf.asJson.spaces2)
  // println(branch.asJson.spaces2)
  println(Seq(1,2,3).asJson.spaces2)
  println(Some("one").asJson.spaces2)
}
