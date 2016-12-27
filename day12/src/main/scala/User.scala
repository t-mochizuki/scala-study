case class User(superUser: Boolean, authorities: Seq[Authority]) {
  def access(site: Site): Boolean =
    if (superUser) true
    else if (site.authority.isEmpty) true
    else authorities.contains(site.authority.get)
}
