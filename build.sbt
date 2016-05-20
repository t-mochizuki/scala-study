lazy val baseSettings = Seq(
    version := "0.0.1",
    scalaVersion := "2.11.8",
    scalacOptions ++= Seq("-language:experimental.macros", "-deprecation", "-feature", "-language:higherKinds", "-unchecked", "-Xlint"),
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
lazy val day5 = (project in file("day5")).
  settings(
    baseSettings,
    libraryDependencies += "org.scala-lang" % "scala-compiler" % scalaVersion.value,
    name := "day5"
  )
lazy val day6 = (project in file("day6")).
  settings(
    baseSettings,
    libraryDependencies += "org.json4s" % "json4s-native_2.11" % "3.3.0",
    name := "day6"
  )
lazy val day7 = (project in file("day7")).
  settings(
    baseSettings,
    libraryDependencies += "io.argonaut" %% "argonaut" % "6.1",
    name := "day7"
  )
val circeVersion = "0.4.1"
lazy val day8 = (project in file("day8")).
  settings(
    baseSettings,
    libraryDependencies ++= Seq(
      "io.circe" %% "circe-core",
      "io.circe" %% "circe-generic",
      "io.circe" %% "circe-parser"
    ).map(_ % circeVersion),
    name := "day8"
  )
