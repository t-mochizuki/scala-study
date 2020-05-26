ThisBuild / scalaVersion := "2.13.2"
ThisBuild / version := "0.1.0"

lazy val hello = taskKey[Unit]("An example task")
lazy val akkaHttpVersion = settingKey[String]("")
lazy val akkaStreamVersion = settingKey[String]("")
lazy val akkaHttpCirceVersion = settingKey[String]("")
lazy val circeVersion = settingKey[String]("")

lazy val baseSettings = Seq(
    name := "example",
    hello := { println("Hello!") },
    akkaHttpVersion := "10.1.12",
    akkaStreamVersion := "2.5.26",
    akkaHttpCirceVersion := "1.32.0",
    circeVersion := "0.12.3")

libraryDependencies ++= Seq(
  "io.circe" %% "circe-core",
  "io.circe" %% "circe-generic",
  "io.circe" %% "circe-parser"
).map(_ % circeVersion.value)

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-http" % akkaHttpVersion.value,
  "com.typesafe.akka" %% "akka-stream" % akkaStreamVersion.value,
  "de.heikoseeberger" %% "akka-http-circe" % akkaHttpCirceVersion.value)

lazy val root = (project in file("."))
  .settings(baseSettings)
