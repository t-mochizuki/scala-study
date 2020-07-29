import sbt._

object Dependencies {

  object Version {
    val akka = "2.6.6"
    val akkaHttp = "10.1.12"
    val akkaHttpSession = "0.5.11"
    val scalatest = "3.2.0"
    val circe = "0.13.0"
    val redisClient = "3.30"
  }

  val akkaHttp = "com.typesafe.akka" %% "akka-http" % Version.akkaHttp
  val akkaHttpTestkit = "com.typesafe.akka" %% "akka-http-testkit" % Version.akkaHttp % Test
  val akkaHttpSession = "com.softwaremill.akka-http-session" %% "core" % Version.akkaHttpSession
  val akkaStream = "com.typesafe.akka" %% "akka-stream" % Version.akka
  val akkaStreamTestKit = "com.typesafe.akka" %% "akka-stream-testkit" % Version.akka % Test
  val scalatestFlatSpec = "org.scalatest" %% "scalatest-flatspec" % Version.scalatest % Test
  val scalatestDiagrams = "org.scalatest" %% "scalatest-diagrams" % Version.scalatest % Test
  val scalatestMustMatchers = "org.scalatest" %% "scalatest-mustmatchers" % Version.scalatest % Test
  val redisClient = "net.debasishg" %% "redisclient" % Version.redisClient
  val circeCore = "io.circe" %% "circe-core" % Version.circe
  val circeGeneric = "io.circe" %% "circe-generic" % Version.circe
  val circeParser = "io.circe" %% "circe-parser" % Version.circe

  val serverDeps = Seq(
    akkaHttp,
    akkaHttpTestkit,
    akkaHttpSession,
    akkaStream,
    akkaStreamTestKit,
    circeCore,
    circeGeneric,
    circeParser,
    redisClient,
    scalatestFlatSpec,
    scalatestDiagrams,
    scalatestMustMatchers)

  val rootDeps = Nil

}
