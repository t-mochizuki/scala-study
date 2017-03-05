import com.typesafe.config.ConfigFactory
import skinny.http._
import io.circe._
import io.circe.generic.auto._
import io.circe.parser._
import io.circe.syntax._

case class OneResponse(accessToken: String)

object HelloSkinnyHttpClient extends App {

  val conf = ConfigFactory.load().getConfig("blaynmail")
  val username = conf.getString("username")
  val password = conf.getString("password")
  val api_key = conf.getString("api_key")
  val format = "json"

  val loginUrl = "https://api.bme.jp/rest/1.0/authenticate/login"
  val response = HTTP.post(loginUrl, "username" -> username, "password" -> password, "api_key" -> api_key, "format" -> format)
  assert(response.status == 401)

  val responseTextBody = "{\"accessToken\":\"6b02e6b331ba2c06ec9d8bf51a6d1af4\"}"
  val accessToken = decode[OneResponse](responseTextBody)
  accessToken match {
    case Left(e) => println(e.toString)
    case Right(v) => println(v.accessToken)
  }

  val logoutUrl = "https://api.bme.jp/rest/1.0/authenticate/logout"
  val response1 = HTTP.get(logoutUrl, "access_token" -> accessToken)
  assert(response1.status == 401)

}
