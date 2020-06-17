import Dependencies._

Global / onChangedBuildSource := ReloadOnSourceChanges
ThisBuild / scalaVersion := "2.13.2"
ThisBuild / version := "0.1.0"

lazy val hello = taskKey[Unit]("An example task")

val unusedWarnings = Set("-Ywarn-unused")

lazy val commonSettings = Seq(
  hello := { println(s"Hello, ${name.value}!") },
  Compile / compile / wartremoverErrors ++= Warts.unsafe,
  Test / fork := true,
  Test / javaOptions ++= Seq("-Dconfig.resource=test.conf"),
  Test / testOptions += Tests.Argument(TestFrameworks.ScalaTest, "-oD"),
  scalacOptions ++= (
    "-deprecation" ::
      "-unchecked" ::
      "-language:existentials" ::
      "-language:higherKinds" ::
      "-language:implicitConversions" ::
      "-Ywarn-unused" ::
      Nil
  ),
  scalacOptions in (Compile, console) ~= (_.filterNot(unusedWarnings)),
  scalacOptions in (Test, console) := (scalacOptions in (Compile, console)).value
)

lazy val base = project
  .in(file("base"))
  .disablePlugins(AssemblyPlugin)
  .settings(
    commonSettings,
    libraryDependencies ++= baseDeps
  )

lazy val root = project
  .in(file("."))
  .disablePlugins(AssemblyPlugin)
  .settings(
    commonSettings,
    flywayUrl := "jdbc:mysql://localhost:3317/sandbox?useSSL=false",
    flywayUser := "user",
    flywayPassword := "password",
    flywayLocations := Seq("filesystem:db/migration"),
    Test / flywayUrl := "jdbc:mysql://localhost:3318/sandbox?useSSL=false",
    Test / flywayUser := "user",
    Test / flywayPassword := "password",
    Test / flywayLocations := Seq("filesystem:db/migration"),
    libraryDependencies ++= rootDeps
  )
  .enablePlugins(FlywayPlugin)
  .aggregate(base)
