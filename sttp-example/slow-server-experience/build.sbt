import Dependencies._

ThisBuild / scalaVersion := "2.13.2"
ThisBuild / version := "0.1.0"

addCommandAlias("ls", ";projects")
addCommandAlias("cd", ";project")

lazy val syncHttpClient = project
  .in(file("sync-http-client"))
  .settings(
    libraryDependencies ++= coreDeps
  )

lazy val asyncHttpClient = project
  .in(file("async-http-client"))
  .settings(
    libraryDependencies ++= coreDeps
  )

lazy val root = project
  .in(file("."))
  .aggregate(
    syncHttpClient,
    asyncHttpClient
  )
