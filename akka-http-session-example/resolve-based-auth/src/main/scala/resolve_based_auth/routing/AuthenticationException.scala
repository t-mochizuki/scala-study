package resolve_based_auth.routing

case class AuthenticationException(message: String) extends Exception(message)
