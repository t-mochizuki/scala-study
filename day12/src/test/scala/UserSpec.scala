import org.scalacheck._
import Prop.forAll

object UserSpec extends Properties("User") {

  def genNormalUser = Gen.oneOf(
    NormalUser(Nil),
    NormalUser(Seq(Power)),
    NormalUser(Seq(Age)),
    NormalUser(Seq(Power, Age)))

  def genSite = Gen.oneOf(Site(None), Site(Some(Power)), Site(Some(Age)))

  property("Normal user access a site") = forAll(genNormalUser, genSite) { (user, site) =>
    val a = site.authority.map(user.authorities.contains(_)).getOrElse(true)
    val b = user.access(site)
    a == b
  }

  property("Super user access a site") = forAll(genSite) { (site) =>
    SuperUser.access(site) == true
  }

}
