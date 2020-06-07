Global / onChangedBuildSource := ReloadOnSourceChanges
ThisBuild / scalaVersion := "2.13.2"
ThisBuild / version := "0.1.0"

wartremoverErrors in (Compile, compile) ++= Warts.unsafe

flywayUrl := "jdbc:postgresql://localhost:5433/sandbox"
flywayUser := "user"
flywayPassword := "password"
flywayUrl in Test := "jdbc:postgresql://localhost:5434/sandbox"
flywayUser in Test := "user"
flywayPassword in Test := "password"
flywayLocations := Seq("db/migration")

lazy val hello = taskKey[Unit]("An example task")

lazy val baseSettings = Seq(name := "example", hello := { println("Hello!") })

lazy val root = (project in file("."))
  .settings(
    baseSettings,
    libraryDependencies ++= Seq(
      "org.scalatest" %% "scalatest" % "3.0.8" % Test,
      "org.postgresql" % "postgresql" % "42.2.13"
    )
  )
  .enablePlugins(FlywayPlugin)
