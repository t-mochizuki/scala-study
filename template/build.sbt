Global / onChangedBuildSource := ReloadOnSourceChanges
Test / fork := true
ThisBuild / scalaVersion := "2.13.2"
ThisBuild / version := "0.1.0"

wartremoverErrors in (Compile, compile) ++= Warts.unsafe

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

lazy val hello = taskKey[Unit]("An example task")

lazy val baseSettings = Seq(
  name := "example",
  flywayUrl := "jdbc:postgresql://localhost:5433/sandbox",
  flywayUser := "user",
  flywayPassword := "password",
  flywayLocations := Seq("filesystem:db/migration"),
  flywayUrl in Test := "jdbc:postgresql://localhost:5434/sandbox",
  flywayUser in Test := "user",
  flywayPassword in Test := "password",
  flywayLocations in Test := Seq("filesystem:db/migration"),
  hello := { println("Hello!") }
)

val scalikejdbcVersion = "3.4.2"
val akkaHttpVersion = "10.1.12"
val akkaVersion = "2.6.6"
val circeVersion = "0.12.3"

lazy val root = (project in file("."))
  .settings(
    baseSettings,
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-stream" % akkaVersion,
      "com.typesafe.akka" %% "akka-slf4j" % akkaVersion,
      "com.typesafe.akka" %% "akka-testkit" % akkaVersion % Test,
      "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
      "de.heikoseeberger" %% "akka-http-circe" % "1.32.0",
      "io.circe" %% "circe-core" % circeVersion,
      "io.circe" %% "circe-generic" % circeVersion,
      "io.circe" %% "circe-parser" % circeVersion,
      "org.scalatest" %% "scalatest" % "3.0.8" % Test,
      "org.postgresql" % "postgresql" % "42.2.13",
      "org.scalikejdbc" %% "scalikejdbc" % scalikejdbcVersion,
      "org.scalikejdbc" %% "scalikejdbc-config" % scalikejdbcVersion,
      "org.scalikejdbc" %% "scalikejdbc-syntax-support-macro" % scalikejdbcVersion,
      "org.scalikejdbc" %% "scalikejdbc-test" % scalikejdbcVersion % Test,
      "ch.qos.logback" % "logback-classic" % "1.2.3"
    )
  )
  .enablePlugins(FlywayPlugin)
