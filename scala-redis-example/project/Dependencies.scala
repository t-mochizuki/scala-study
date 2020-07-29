import sbt._

object Dependencies {

  object Version {
    val circe = "0.13.0"
  }

  val redisclient = "net.debasishg" %% "redisclient" % "3.30"

  val circeCore = "io.circe" %% "circe-core" % Version.circe
  val circeGeneric = "io.circe" %% "circe-generic" % Version.circe
  val circeParser = "io.circe" %% "circe-parser" % Version.circe

  val scalatest = "org.scalatest" %% "scalatest" % "3.0.8" % Test

  val rootDeps = Seq(
    redisclient,
    circeCore,
    circeGeneric,
    circeParser,
    scalatest
  )

}
