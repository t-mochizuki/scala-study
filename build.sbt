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
lazy val day2 = (project in file("day2")).
  settings(
    baseSettings,
    name := "day2"
  )
lazy val day3 = (project in file("day3")).
  settings(
    baseSettings,
    addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full),
    name := "day3"
  )
lazy val day4 = (project in file("day4")).
  settings(
    baseSettings,
    libraryDependencies += "com.chuusai" %% "shapeless" % "2.3.0",
    name := "day4"
  )
