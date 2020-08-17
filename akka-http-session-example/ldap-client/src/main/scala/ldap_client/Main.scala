package ldap_client

import com.typesafe.config.ConfigFactory
import com.unboundid.ldap.sdk._

object Main extends App {

  val config = ConfigFactory.load
  val ldapHost = config.getString("ldap.host")
  val ldapPort = config.getInt("ldap.port")

  def using[A, B](s: A)(f: A => B)(implicit ev: A => { def close(): Unit }): B = {
    try {
      f(s)
    } finally {
      s.close()
    }
  }

  val result = using(new LDAPConnection(ldapHost, ldapPort)) { connection =>
    val bindDN = args(0)
    val password = args(1)

    try {
      connection.bind(bindDN, password)
    } catch { case e: LDAPException =>
      new BindResult(e)
    }
  }

  println(result)

}
