import sbt._

object Dependencies {

  object Version {
    val akka = "2.6.6"
    val akkaHttp = "10.1.12"
    val akkaHttpCirce = "1.32.0"
    val akkaHttpCors = "1.1.0"
    val akkaHttpSession = "0.5.11"
    val circe = "0.13.0"
    val config = "1.4.0"
    val redisClient = "3.30"
    val sangria = "2.0.0"
    val sangriaCirce = "1.3.0"
    val scalatest = "3.2.0"
    val unboundidLdapsdk = "5.1.0"
  }

  val akkaHttp = "com.typesafe.akka" %% "akka-http" % Version.akkaHttp
  val akkaHttpCirce = "de.heikoseeberger" %% "akka-http-circe" % Version.akkaHttpCirce
  val akkaHttpCors = "ch.megard" %% "akka-http-cors" % Version.akkaHttpCors
  val akkaHttpSession = "com.softwaremill.akka-http-session" %% "core" % Version.akkaHttpSession
  val akkaHttpTestkit = "com.typesafe.akka" %% "akka-http-testkit" % Version.akkaHttp % Test
  val akkaStream = "com.typesafe.akka" %% "akka-stream" % Version.akka
  val akkaStreamTestKit = "com.typesafe.akka" %% "akka-stream-testkit" % Version.akka % Test
  val circeCore = "io.circe" %% "circe-core" % Version.circe
  val circeGeneric = "io.circe" %% "circe-generic" % Version.circe
  val circeParser = "io.circe" %% "circe-parser" % Version.circe
  val config = "com.typesafe" % "config" % Version.config
  val redisClient = "net.debasishg" %% "redisclient" % Version.redisClient
  val sangria = "org.sangria-graphql" %% "sangria" % Version.sangria
  val sangriaCirce = "org.sangria-graphql" %% "sangria-circe" % Version.sangriaCirce
  val scalatestDiagrams = "org.scalatest" %% "scalatest-diagrams" % Version.scalatest % Test
  val scalatestFlatSpec = "org.scalatest" %% "scalatest-flatspec" % Version.scalatest % Test
  val scalatestMustMatchers = "org.scalatest" %% "scalatest-mustmatchers" % Version.scalatest % Test
  val unboundidLdapsdk = "com.unboundid" % "unboundid-ldapsdk" % Version.unboundidLdapsdk

  val ldapDeps = Seq(
    config,
    unboundidLdapsdk
  )

  val scalatestDeps = Seq(
    scalatestFlatSpec,
    scalatestDiagrams,
    scalatestMustMatchers)

  val coreDeps = Seq(
    redisClient,
    circeCore,
    circeGeneric,
    circeParser)

  val graphqlDeps = Seq(
    sangria,
    sangriaCirce)

  val serverDeps = Seq(
    akkaHttp,
    akkaHttpCirce,
    akkaHttpCors,
    akkaHttpTestkit,
    akkaHttpSession,
    akkaStream,
    akkaStreamTestKit) ++ coreDeps ++ scalatestDeps

  val rootDeps = Nil

}
