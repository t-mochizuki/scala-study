import sbt._

object Dependencies {

  val scalatest = "org.scalatest" %% "scalatest" % "3.2.0" % Test
  val scalatestFlatspec = "org.scalatest" %% "scalatest-flatspec" % "3.2.0" % Test
  val mockito = "org.mockito" % "mockito-core" % "3.4.0" % Test

  val rootDeps = Seq(
    scalatest,
    scalatestFlatspec,
    mockito
  )

}
