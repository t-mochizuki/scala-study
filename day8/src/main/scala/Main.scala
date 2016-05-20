import cats.data.Xor
import io.circe.generic.auto._
import io.circe.syntax._
import io.circe.parser._

object Main extends App {
  case class Address(street: String, number: Int, postcode: Int)
  case class Person(name: String, age: Int, address: Option[Address], greeting: Option[String])
  sealed trait Tree[+A]
  case class Leaf[A](value: A) extends Tree[A]
  case class Branch[A](left: Tree[A], right: Tree[A]) extends Tree[A]

  val address = Address("Shinjuku", 1, 2)
  val parson = Person("Taro", 10, Some(address), None)
  val leftLeaf = Leaf("left leaf")
  val rightLeaf = Leaf("right leaf")
  val branch = Branch(leftLeaf, rightLeaf)

  println(address.asJson.spaces4)
  println(parson.asJson.noSpaces)
  println(leftLeaf.asJson)
  println(branch.asJson)

  assert(decode[Branch[String]](branch.asJson.toString) == Xor.Right(branch))
}
