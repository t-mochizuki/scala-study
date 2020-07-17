import sbt._

object Dependencies {

  val scalatest = "org.scalatest" %% "scalatest" % "3.0.8" % Test
  val mockito = "org.mockito" % "mockito-core" % "3.4.0" % Test

  val rootDeps = Seq(
    scalatest,
    mockito
  )

}
