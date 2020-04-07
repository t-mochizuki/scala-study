ThisBuild / scalaVersion := "2.12.11"
ThisBuild / version := "0.1.0"

lazy val hello = taskKey[Unit]("An example task")

val sttpClientVersion = "2.0.7"

libraryDependencies += "com.softwaremill.sttp.client" %% "core" % sttpClientVersion

lazy val root = (project in file("."))
  .settings(
    name := "example",
    hello := { println("Hello!") }
  )
