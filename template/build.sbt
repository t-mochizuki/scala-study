Global / onChangedBuildSource := ReloadOnSourceChanges
ThisBuild / scalaVersion := "2.13.2"
ThisBuild / version := "0.1.0"

wartremoverErrors in (Compile, compile) ++= Warts.unsafe

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

lazy val root = (project in file("."))
  .settings(
    baseSettings,
    libraryDependencies ++= Seq(
      "org.scalatest"   %% "scalatest"                        % "3.0.8" % Test,
      "org.postgresql"   % "postgresql"                       % "42.2.13",
      "org.scalikejdbc" %% "scalikejdbc"                      % "3.4.2",
      "org.scalikejdbc" %% "scalikejdbc-config"               % "3.4.2",
      "org.scalikejdbc" %% "scalikejdbc-syntax-support-macro" % "3.4.2",
      "org.scalikejdbc" %% "scalikejdbc-test"                 % "3.4.2" % Test,
      "ch.qos.logback"   % "logback-classic"                  % "1.2.3"
    )
  )
  .enablePlugins(FlywayPlugin)
