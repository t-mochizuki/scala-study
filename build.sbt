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
lazy val day10 = (project in file("day10")).
  settings(
    baseSettings,
    libraryDependencies += "org.scalacheck" %% "scalacheck" % "1.13.0" % "test",
    name := "day10"
  )
lazy val day11 = (project in file("day11")).
  settings(
    baseSettings,
    libraryDependencies += "org.scalacheck" %% "scalacheck" % "1.13.0" % "test",
    name := "day11"
  )
lazy val day12 = (project in file("day12")).
  settings(
    baseSettings,
    libraryDependencies += "org.scalacheck" %% "scalacheck" % "1.13.0" % "test",
    name := "day12"
  )
lazy val day13 = (project in file("day13")).
  settings(
    baseSettings,
    libraryDependencies += "com.typesafe.akka" %% "akka-http" % "10.0.1",
    name := "day13"
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
lazy val day9 = (project in file("day9")).
  settings(
    baseSettings,
    libraryDependencies ++= Seq(
      "org.scalactic" %% "scalactic" % "2.2.6",
      "org.scalatest" %% "scalatest" % "2.2.6" % "test"
    ),
    name := "day9"
  )
