ThisBuild / scalaVersion := "2.12.11"
ThisBuild / version := "0.1.0"

lazy val hello = taskKey[Unit]("An example task")

val json4sJackson = "org.json4s" %% "json4s-jackson" % "3.7.0-M2"

libraryDependencies += json4sJackson

lazy val root = (project in file("."))
  .settings(
    name := "example",
    hello := { println("Hello!") }
  )
