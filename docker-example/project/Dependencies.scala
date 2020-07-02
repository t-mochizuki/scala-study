import sbt._

object Dependencies {

  object Version {
    val akka = "2.6.6"
    val akkaHttp = "10.1.12"
    val circe = "0.13.0"
    val slf4j = "1.7.30"
    val logback = "1.2.3"
  }

  val akkaStream = "com.typesafe.akka" %% "akka-stream" % Version.akka

  val akkaHttp = "com.typesafe.akka" %% "akka-http" % Version.akkaHttp

  val akkaHttpCirce = "de.heikoseeberger" %% "akka-http-circe" % "1.32.0"

  val circeCore = "io.circe" %% "circe-core" % Version.circe
  val circeGeneric = "io.circe" %% "circe-generic" % Version.circe
  val circeParser = "io.circe" %% "circe-parser" % Version.circe

  val slf4jApi = "org.slf4j" % "slf4j-api" % Version.slf4j

  val logbackCore = "ch.qos.logback" % "logback-core" % Version.logback
  val logbackClassic = "ch.qos.logback" % "logback-classic" % Version.logback

  val serverDeps = Seq(
    akkaStream,
    akkaHttp,
    akkaHttpCirce,
    circeCore,
    circeGeneric,
    circeParser,
    slf4jApi,
    logbackCore,
    logbackClassic
  )

  val rootDeps = Seq(
  )

}
