ThisBuild / scalaVersion := "2.12.11"
ThisBuild / version := "0.1.0"

lazy val hello = taskKey[Unit]("An example task")

val circeVersion = "0.12.3"

lazy val root = (project in file("."))
  .settings(
    name := "example",
    hello := { println("Hello!") }
  )
