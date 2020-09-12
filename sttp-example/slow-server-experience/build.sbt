import Dependencies._

ThisBuild / scalaVersion := "2.13.2"
ThisBuild / version := "0.1.0"

addCommandAlias("ls", ";projects")
addCommandAlias("cd", ";project")

lazy val core = project
  .in(file("core"))
  .settings(
    libraryDependencies ++= coreDeps
  )

lazy val syncHttpClient = project
  .in(file("sync-http-client"))
  .dependsOn(core % "compile->compile;test->test")

lazy val asyncHttpClient = project
  .in(file("async-http-client"))
  .dependsOn(core % "compile->compile;test->test")

lazy val root = project
  .in(file("."))
  .aggregate(
    syncHttpClient,
    asyncHttpClient
  )
