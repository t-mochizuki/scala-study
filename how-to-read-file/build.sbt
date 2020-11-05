Global / onChangedBuildSource := IgnoreSourceChanges

ThisBuild / scalaVersion := "2.13.3"
ThisBuild / version := "0.1.0"

addCommandAlias("ls", ";projects")
addCommandAlias("fmt", ";scalafmtAll;scalafmtSbt")

val unusedWarnings = Set("-Ywarn-unused")

lazy val commonSettings = Seq(
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

lazy val root = project
  .in(file("."))
  .settings(
    commonSettings
  )
