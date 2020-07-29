import sbt._

object Dependencies {

  object Version {
    val akka = "2.6.6"
    val akkaHttp = "10.1.12"
    val akkaHttpSession = "0.5.11"
    val scalatest = "3.2.0"
  }

  val akkaHttp = "com.typesafe.akka" %% "akka-http" % Version.akkaHttp
  val akkaHttpTestkit = "com.typesafe.akka" %% "akka-http-testkit" % Version.akkaHttp % Test
  val akkaHttpSession = "com.softwaremill.akka-http-session" %% "core" % Version.akkaHttpSession
  val akkaStream = "com.typesafe.akka" %% "akka-stream" % Version.akka
  val akkaStreamTestKit = "com.typesafe.akka" %% "akka-stream-testkit" % Version.akka % Test
  val scalatestFlatSpec = "org.scalatest" %% "scalatest-flatspec" % Version.scalatest % Test
  val scalatestDiagrams = "org.scalatest" %% "scalatest-diagrams" % Version.scalatest % Test
  val scalatestMustMatchers = "org.scalatest" %% "scalatest-mustmatchers" % Version.scalatest % Test

  val serverDeps = Seq(
    akkaHttp,
    akkaHttpTestkit,
    akkaHttpSession,
    akkaStream,
    akkaStreamTestKit,
    scalatestFlatSpec,
    scalatestDiagrams,
    scalatestMustMatchers)

  val rootDeps = Nil

}
