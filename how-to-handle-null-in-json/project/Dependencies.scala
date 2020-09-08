import sbt._

object Dependencies {

  object Version {
    val circe = "0.13.0"
    val scalatest = "3.2.0"
  }

  val circeCore = "io.circe" %% "circe-core" % Version.circe
  val circeGeneric = "io.circe" %% "circe-generic" % Version.circe
  val circeParser = "io.circe" %% "circe-parser" % Version.circe
  val scalatestDiagrams = "org.scalatest" %% "scalatest-diagrams" % Version.scalatest % Test
  val scalatestFlatSpec = "org.scalatest" %% "scalatest-flatspec" % Version.scalatest % Test

  val coreDeps = Seq(
    circeCore,
    circeGeneric,
    circeParser,
    scalatestDiagrams,
    scalatestFlatSpec
  )

}
