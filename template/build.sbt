ThisBuild / scalaVersion := "2.13.2"
ThisBuild / version := "0.1.0"

lazy val hello = taskKey[Unit]("An example task")

lazy val baseSettings = Seq(
    name := "example",
    hello := { println("Hello!") })

lazy val root = (project in file("."))
  .settings(baseSettings)
