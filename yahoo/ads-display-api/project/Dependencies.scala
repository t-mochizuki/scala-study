import sbt._

object Dependencies {

  object Version {
    val circe = "0.13.0"
    val sttpClient = "2.2.7"
  }

  val circeCore = "io.circe" %% "circe-core" % Version.circe
  val circeGeneric = "io.circe" %% "circe-generic" % Version.circe
  val circeParser = "io.circe" %% "circe-parser" % Version.circe
  val sttpClientCore = "com.softwaremill.sttp.client" %% "core" % Version.sttpClient
  val sttpClientCirce = "com.softwaremill.sttp.client" %% "circe" % Version.sttpClient

  val coreDeps = Seq(
    circeCore,
    circeGeneric,
    circeParser,
    sttpClientCore,
    sttpClientCirce
  )

}
