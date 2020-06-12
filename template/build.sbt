import Dependencies._

Global / onChangedBuildSource := ReloadOnSourceChanges
ThisBuild / scalaVersion := "2.13.2"
ThisBuild / version := "0.1.0"

lazy val hello = taskKey[Unit]("An example task")

Compile / compile / wartremoverErrors ++= Warts.unsafe

scalacOptions ++= (
  "-deprecation" ::
    "-unchecked" ::
    "-language:existentials" ::
    "-language:higherKinds" ::
    "-language:implicitConversions" ::
    "-Ywarn-unused" ::
    Nil
)

val unusedWarnings = (
  "-Ywarn-unused" ::
    Nil
)

scalacOptions in (Compile, console) ~= (_.filterNot(unusedWarnings.toSet))
scalacOptions in (Test, console) := (scalacOptions in (Compile, console)).value

Test / javaOptions += "-Dconfig.resources=test.conf"

lazy val flywaySettings = Seq(
  flywayUrl := "jdbc:postgresql://localhost:5433/sandbox",
  flywayUser := "user",
  flywayPassword := "password",
  flywayLocations := Seq("filesystem:db/migration"),
  Test / flywayUrl := "jdbc:postgresql://localhost:5434/sandbox",
  Test / flywayUser := "user",
  Test / flywayPassword := "password",
  Test / flywayLocations := Seq("filesystem:db/migration")
)

lazy val base = project
  .in(file("base"))
  .disablePlugins(AssemblyPlugin)
  .settings(
    hello := { println(s"Hello, ${baseDirectory.value}!") },
    Test / fork := true,
    libraryDependencies ++= baseDeps
  )

lazy val fooClient = project
  .in(file("foo-client"))
  .settings(
    hello := { println(s"Hello, ${baseDirectory.value}!") },
    Test / fork := true
  )
  .dependsOn(base % "compile->compile;test->test")

lazy val barServer = project
  .in(file("bar-server"))
  .settings(
    hello := { println(s"Hello, ${baseDirectory.value}!") },
    Test / fork := true
  )
  .dependsOn(base % "compile->compile;test->test")

lazy val root = project
  .in(file("."))
  .disablePlugins(AssemblyPlugin)
  .settings(
    hello := { println(s"Hello, ${baseDirectory.value}!") },
    Test / fork := true,
    flywaySettings,
    libraryDependencies ++= rootDeps
  )
  .enablePlugins(FlywayPlugin)
  .aggregate(base, barServer, fooClient)
