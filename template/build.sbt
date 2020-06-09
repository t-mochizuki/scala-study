Global / onChangedBuildSource := ReloadOnSourceChanges
ThisBuild / scalaVersion := "2.13.2"
ThisBuild / version := "0.1.0"

wartremoverErrors in (Compile, compile) ++= Warts.unsafe

scalacOptions ++= Seq("-deprecation", "-Ywarn-unused")

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

lazy val root = (project in file("."))
  .settings(
    baseSettings,
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-stream" % akkaVersion,
      "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
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
