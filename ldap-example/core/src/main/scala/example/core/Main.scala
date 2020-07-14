package example.core

import com.typesafe.config.ConfigFactory
import com.unboundid.ldap.sdk._

object Main extends App {

  val config = ConfigFactory.load
  val ldapHost = config.getString("ldap.host")
  val ldapPort = config.getInt("ldap.port")

  @SuppressWarnings(Array("org.wartremover.warts.NonUnitStatements"))
  def using[A](s: A)(f: A => Any)(implicit ev: A => { def close(): Unit }): Unit = {
    try {
      f(s)
    } catch { case e: LDAPException =>
      println(e.getMessage)
    } finally {
      s.close()
      println("closed")
    }
  }


  val bindDN = args(0)
  val password = args(1)

  using(new LDAPConnection(ldapHost, ldapPort)) { connection =>
    connection.bind(bindDN, password)
  }

}
