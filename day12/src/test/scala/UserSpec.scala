import org.scalacheck._
import Prop.forAll

object UserSpec extends Properties("User") {

  def genUser = Gen.oneOf(
    User(false, Nil),
    User(false, Seq(Power)),
    User(false, Seq(Age)),
    User(false, Seq(Power, Age)),
    User(true, Nil))

  def genSite = Gen.oneOf(Site(None), Site(Some(Power)), Site(Some(Age)))

  property("constraint") = forAll(genUser, genSite) { (user, site) =>
    val a =
      if (user.superUser) true
      else if (site.authority.isEmpty) true
      else user.authorities.contains(site.authority.get)
    val b = user.access(site)
    a == b
  }

}
