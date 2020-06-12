Global / onChangedBuildSource := ReloadOnSourceChanges
ThisBuild / scalaVersion := "2.13.2"
ThisBuild / version := "0.1.0"

lazy val hello = taskKey[Unit]("An example task")

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

javaOptions in Test += "-Dconfig.resources=test.conf"

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

val scalikejdbcVersion = "3.4.2"
val akkaHttpVersion = "10.1.12"
val akkaVersion = "2.6.6"
val circeVersion = "0.12.3"
val postgresqlVersion = "42.2.13"
val slf4jVersion = "1.7.30"
val logbackVersion = "1.2.3"

lazy val base = project
  .in(file("base"))
  .disablePlugins(AssemblyPlugin)
  .settings(
    hello := { println(s"Hello, ${baseDirectory.value}!") },
    Test / fork := true,
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
      "org.postgresql" % "postgresql" % postgresqlVersion,
      "org.scalikejdbc" %% "scalikejdbc" % scalikejdbcVersion,
      "org.scalikejdbc" %% "scalikejdbc-config" % scalikejdbcVersion,
      "org.scalikejdbc" %% "scalikejdbc-syntax-support-macro" % scalikejdbcVersion,
      "org.scalikejdbc" %% "scalikejdbc-test" % scalikejdbcVersion % Test,
      "org.slf4j" % "slf4j-api" % slf4jVersion,
      "ch.qos.logback" % "logback-core" % logbackVersion,
      "ch.qos.logback" % "logback-classic" % logbackVersion
    )
  )

lazy val root = project
  .in(file("."))
  .disablePlugins(AssemblyPlugin)
  .settings(
    hello := { println(s"Hello, ${baseDirectory.value}!") },
    Test / fork := true,
    flywaySettings,
    libraryDependencies ++= Seq("org.postgresql" % "postgresql" % postgresqlVersion)
  )
  .enablePlugins(FlywayPlugin)
  .aggregate(base)
