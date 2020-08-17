package ldap_client

import com.typesafe.config.ConfigFactory

object Main extends App {

  val config = ConfigFactory.load
  val ldapHost = config.getString("ldap.host")
  val ldapPort = config.getInt("ldap.port")

  val bindDN = args(0)
  val password = args(1)

  println(LdapClient(ldapHost, ldapPort).authenticate(bindDN, password))

}
