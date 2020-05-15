ThisBuild / scalaVersion := "2.12.11"
ThisBuild / version := "0.1.0"

lazy val hello = taskKey[Unit]("An example task")

libraryDependencies ++= Seq(
  "org.sangria-graphql" %% "sangria" % "1.4.2",
  "org.sangria-graphql" %% "sangria-circe" % "1.2.1")

lazy val root = (project in file("."))
  .settings(
    name := "example",
    hello := { println("Hello!") }
  )
