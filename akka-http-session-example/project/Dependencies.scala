import sbt._

object Dependencies {

  object Version {
    val akka = "2.6.6"
    val akkaHttp = "10.1.12"
  }

  val akkaHttp = "com.typesafe.akka" %% "akka-http" % Version.akkaHttp
  val akkaHttpTestkit = "com.typesafe.akka" %% "akka-http-testkit" % Version.akkaHttp % Test
  val akkaStream = "com.typesafe.akka" %% "akka-stream" % Version.akka
  val akkaStreamTestKit = "com.typesafe.akka" %% "akka-stream-testkit" % Version.akka % Test
  val scalatest = "org.scalatest" %% "scalatest" % "3.0.8" % Test

  val serverDeps = Seq(
    akkaHttp,
    akkaStream,
    scalatest)

  val rootDeps = Nil

}
