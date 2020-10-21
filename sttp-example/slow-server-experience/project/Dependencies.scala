import sbt._

object Dependencies {

  object Version {
    val akka = "2.6.9"
    val circe = "0.13.0"
    val sttpClient = "3.0.0-RC2"
    val logback = "1.2.3"
  }

  val akkaStream = "com.typesafe.akka" %% "akka-stream" % Version.akka
  val circeCore = "io.circe" %% "circe-core" % Version.circe
  val circeGeneric = "io.circe" %% "circe-generic" % Version.circe
  val circeParser = "io.circe" %% "circe-parser" % Version.circe
  val logbackCore = "ch.qos.logback" % "logback-core" % Version.logback
  val logbackClassic = "ch.qos.logback" % "logback-classic" % Version.logback
  val sttpClientAkkaHttpBackend = "com.softwaremill.sttp.client" %% "akka-http-backend" % Version.sttpClient
  val sttpClientCirce = "com.softwaremill.sttp.client" %% "circe" % Version.sttpClient

  val coreDeps = Seq(
    akkaStream,
    circeCore,
    circeGeneric,
    circeParser,
    logbackCore,
    logbackClassic,
    sttpClientAkkaHttpBackend,
    sttpClientCirce
  )

}
