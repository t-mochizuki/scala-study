lazy val baseSettings = Seq(
    version := "0.0.1",
    scalaVersion := "2.11.7",
    scalacOptions ++= Seq("-language:experimental.macros", "-deprecation"),
    libraryDependencies += "org.scala-lang" % "scala-reflect" % scalaVersion.value
  )
lazy val day1 = (project in file("day1")).
  settings(
    baseSettings,
    name := "day1"
  )
