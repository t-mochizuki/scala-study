Global / onChangedBuildSource := ReloadOnSourceChanges

ThisBuild / scalaVersion := "2.13.2"
ThisBuild / version := "0.1.0"

lazy val hello = taskKey[Unit]("An example task")
lazy val akkaVersion = settingKey[String]("")
lazy val akkaHttpVersion = settingKey[String]("")

lazy val baseSettings = Seq(
    name := "example",
    hello := { println("Hello!") },
    akkaVersion := "2.5.26",
    akkaHttpVersion := "10.1.12")

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.0.8" % Test,
  "com.typesafe.akka" %% "akka-stream-testkit" % akkaVersion.value % Test,
  "com.typesafe.akka" %% "akka-http-testkit" % akkaHttpVersion.value % Test)

lazy val root = (project in file("."))
  .settings(baseSettings)
