Global / onChangedBuildSource := ReloadOnSourceChanges
ThisBuild / scalaVersion := "2.13.2"
ThisBuild / version := "0.1.0"

lazy val hello = taskKey[Unit]("An example task")

libraryDependencies += "com.typesafe" % "config" % "1.4.0"
libraryDependencies += "com.iheart"  %% "ficus"  % "1.4.7"

lazy val baseSettings = Seq(name := "example", hello := { println("Hello!") })

lazy val root = (project in file("."))
  .settings(baseSettings)
