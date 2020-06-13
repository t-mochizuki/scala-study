import Dependencies._

Global / onChangedBuildSource := ReloadOnSourceChanges
ThisBuild / scalaVersion := "2.13.2"
ThisBuild / version := "0.1.0"

lazy val hello = taskKey[Unit]("An example task")

val unusedWarnings = (
  "-Ywarn-unused" ::
    Nil
)

lazy val commonSettings = Seq(
  hello := { println(s"Hello, ${baseDirectory.value}!") },
  Compile / compile / wartremoverErrors ++= Warts.unsafe,
  Test / fork := true,
  Test / javaOptions ++= Seq("-Dconfig.resource=test.conf"),
  scalacOptions ++= (
    "-deprecation" ::
      "-unchecked" ::
      "-language:existentials" ::
      "-language:higherKinds" ::
      "-language:implicitConversions" ::
      "-Ywarn-unused" ::
      Nil
  ),
  scalacOptions in (Compile, console) ~= (_.filterNot(unusedWarnings.toSet)),
  scalacOptions in (Test, console) := (scalacOptions in (Compile, console)).value
)

lazy val base = project
  .in(file("base"))
  .disablePlugins(AssemblyPlugin)
  .settings(
    commonSettings,
    libraryDependencies ++= baseDeps
  )

lazy val fooClient = project
  .in(file("foo-client"))
  .settings(
    commonSettings
  )
  .dependsOn(base % "compile->compile;test->test")

lazy val barServer = project
  .in(file("bar-server"))
  .settings(
    commonSettings
  )
  .dependsOn(base % "compile->compile;test->test")

lazy val app = project
  .in(file("app"))
  .disablePlugins(AssemblyPlugin)
  .settings(
    commonSettings,
    flywayUrl := "jdbc:mysql://localhost:3307/sandbox",
    flywayUser := "user",
    flywayPassword := "password",
    flywayLocations := Seq("filesystem:db/migration"),
    Test / flywayUrl := "jdbc:mysql://localhost:3308/sandbox",
    Test / flywayUser := "user",
    Test / flywayPassword := "password",
    Test / flywayLocations := Seq("filesystem:db/migration"),
    libraryDependencies ++= appDeps
  )
  .enablePlugins(FlywayPlugin)

lazy val root = project
  .in(file("."))
  .disablePlugins(AssemblyPlugin)
  .settings(
    commonSettings,
    flywayUrl := "jdbc:postgresql://localhost:5433/sandbox",
    flywayUser := "user",
    flywayPassword := "password",
    flywayLocations := Seq("filesystem:db/migration"),
    Test / flywayUrl := "jdbc:postgresql://localhost:5434/sandbox",
    Test / flywayUser := "user",
    Test / flywayPassword := "password",
    Test / flywayLocations := Seq("filesystem:db/migration"),
    libraryDependencies ++= rootDeps
  )
  .enablePlugins(FlywayPlugin)
  .aggregate(base, barServer, fooClient)
