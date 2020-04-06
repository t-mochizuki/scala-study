ThisBuild / organization := "com.example"
ThisBuild / scalaVersion := "2.12.11"
ThisBuild / version := "0.1.0"

lazy val hello = taskKey[Unit]("An example task")

lazy val root = (project in file("."))
  .settings(
    name := "Example",
    hello := { println("Hello!") }
  )
