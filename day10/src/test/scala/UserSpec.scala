import org.scalacheck._
import Prop.forAll

object UserSpec extends Properties("User") {

  def genName: Gen[Name] = Gen.nonEmptyListOf(Gen.alphaChar).map(xs => Name.fromString(xs.mkString))
  def genAge: Gen[Age] = Gen.choose(18, 120).map(Age.fromInt)

  property("constraint") = forAll(genName, genAge) { (name: Name, age: Age) =>
    val user = User(name, age)
    user.name.value != "" && 18 <= user.age.value
  }

}
