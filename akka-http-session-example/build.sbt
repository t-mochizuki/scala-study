import Dependencies._

Global / onChangedBuildSource := ReloadOnSourceChanges
ThisBuild / scalaVersion := "2.13.2"
ThisBuild / version := "0.1.0"

addCommandAlias("fmt", ";scalafmtAll;scalafmtSbt")

val unusedWarnings = Set("-Ywarn-unused")

lazy val commonSettings = Seq(
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

lazy val resolveBasedAuth = project
  .in(file("resolve-based-auth"))
  .settings(
    commonSettings,
    libraryDependencies ++= serverDeps ++ graphqlDeps
  )

lazy val ldapClient = project
  .in(file("ldap-client"))
  .settings(
    commonSettings,
    libraryDependencies ++= ldapDeps
  )

lazy val redisClient = project
  .in(file("redis-client"))
  .settings(
    commonSettings,
    libraryDependencies ++= coreDeps
  )

lazy val csrfProtection = project
  .in(file("csrf-protection"))
  .settings(
    commonSettings,
    libraryDependencies ++= serverDeps
  )

lazy val oneoffHeader = project
  .in(file("oneoff-header"))
  .settings(
    commonSettings,
    libraryDependencies ++= serverDeps
  )

lazy val refreshableCookie = project
  .in(file("refreshable-cookie"))
  .settings(
    commonSettings,
    libraryDependencies ++= serverDeps
  )

lazy val root = project
  .in(file("."))
  .settings(
    commonSettings,
    libraryDependencies ++= rootDeps
  )
  .aggregate(
    refreshableCookie,
    oneoffHeader,
    csrfProtection,
    redisClient,
    ldapClient)
