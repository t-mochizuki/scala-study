import sbt._

object Dependencies {

  object Version {}

  val redisclient = "net.debasishg" %% "redisclient" % "3.30"

  val scalatest = "org.scalatest" %% "scalatest" % "3.0.8" % Test

  val rootDeps = Seq(
    redisclient,
    scalatest
  )

}
