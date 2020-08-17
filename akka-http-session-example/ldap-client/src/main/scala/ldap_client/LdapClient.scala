package ldap_client

import com.unboundid.ldap.sdk._

case class LdapClient(host: String, port: Int) {

  def using[A, B](s: A)(f: A => B)(implicit ev: A => { def close(): Unit }): B = {
    try {
      f(s)
    } finally {
      s.close()
    }
  }

  def authenticate(bindDN: String, password: String): Boolean = {
    val bindResult = using(new LDAPConnection(host, port)) { connection =>
      try {
        connection.bind(bindDN, password)
      } catch { case e: LDAPException =>
        new BindResult(e)
      }
    }

    bindResult.getResultCode.equals(ResultCode.SUCCESS)
  }

}
