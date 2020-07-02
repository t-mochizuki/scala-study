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

val assemblySetting = Seq(
  assembly / test := {},
  assembly / assemblyJarName := name.value + ".jar",
  assembly / assemblyMergeStrategy := {
    case PathList("javax", "servlet", xs @ _*) => MergeStrategy.first
    case PathList(ps @ _*) if ps.last endsWith ".html" => MergeStrategy.first
    case "application.conf" => MergeStrategy.concat
    case "unwanted.txt" => MergeStrategy.discard
    case x =>
      val oldStrategy = (assemblyMergeStrategy in assembly).value
      oldStrategy(x)
  }
)

lazy val dockerSetting = Seq(
  dockerfile in docker := {
    val artifact: File = assembly.value
    val artifactTargetPath = s"/app/${artifact.name}"
    new Dockerfile {
      from("openjdk:11-jre")
      add(artifact, artifactTargetPath)
      expose(8181)
      cmd("java", "-jar", artifactTargetPath)
    }
  },
  buildOptions in docker := BuildOptions(
    cache = false,
    removeIntermediateContainers = BuildOptions.Remove.Always,
    pullBaseImage = BuildOptions.Pull.Always
  )
)

lazy val server = project
  .in(file("server"))
  .enablePlugins(DockerPlugin)
  .settings(
    commonSettings,
    assemblySetting,
    dockerSetting,
    libraryDependencies ++= serverDeps
  )

lazy val root = project
  .in(file("."))
  .disablePlugins(AssemblyPlugin)
  .settings(
    commonSettings,
    libraryDependencies ++= rootDeps
  )
  .aggregate(server)
