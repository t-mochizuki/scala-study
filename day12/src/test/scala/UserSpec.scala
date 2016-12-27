import org.scalacheck._
import Prop.forAll

object UserSpec extends Properties("User") {

  def genAuthorities = Gen.containerOf[Set, Authority](Gen.oneOf(Power, Age))
  def genNormalUser = for (authorities <- genAuthorities) yield NormalUser(authorities)
  def genAuthority = Gen.option[Authority](Gen.oneOf(Power, Age))
  def genSite = for (authority <- genAuthority) yield Site(authority)

  property("Normal user access a site") = forAll(genNormalUser, genSite) { (user, site) =>
    val a = site.authority.map(user.authorities.contains(_)).getOrElse(true)
    val b = user.access(site)
    a == b
  }

  property("Super user access a site") = forAll(genSite) { (site) =>
    SuperUser.access(site) == true
  }

}
