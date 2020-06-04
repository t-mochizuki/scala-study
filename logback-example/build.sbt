ThisBuild / scalaVersion := "2.13.2"
ThisBuild / version := "0.1.0"

lazy val hello = taskKey[Unit]("An example task")
lazy val logbackClassicVersion = settingKey[String]("")

lazy val baseSettings = Seq(
    name := "example",
    hello := { println("Hello!") },
    logbackClassicVersion := "1.2.3")

lazy val root = project
  .in(file("."))
  .settings(baseSettings)
  .aggregate(logging, foo, bar)

lazy val logging = project
  .in(file("logging"))
  .settings(
    baseSettings,
    libraryDependencies ++= Seq(
      "ch.qos.logback" % "logback-classic" % logbackClassicVersion.value))

lazy val foo = project
  .in(file("foo"))
  .settings(
    baseSettings,
    libraryDependencies ++= Seq(
      "ch.qos.logback" % "logback-classic" % logbackClassicVersion.value))
  .dependsOn(
    logging % "compile->compile;test->test"
  )

lazy val bar = project
  .in(file("bar"))
  .settings(
    baseSettings,
    libraryDependencies ++= Seq(
      "ch.qos.logback" % "logback-classic" % logbackClassicVersion.value))
  .dependsOn(
    logging % "compile->compile;test->test"
  )

