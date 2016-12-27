sealed trait User {
  def access(site: Site): Boolean
}

case class NormalUser(authorities: Set[Authority]) extends User {
  def access(site: Site): Boolean = site.authority.map(authorities.contains(_)).getOrElse(true)
}

case object SuperUser extends User {
  def access(site: Site): Boolean = true
}
