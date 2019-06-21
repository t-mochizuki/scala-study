name := "scala-stydy"
version := "0.0.1"

ThisBuild / scalaVersion := "2.12.8"

lazy val settings = Seq(
  scalacOptions ++= Seq(
    "-deprecation",
    "-feature",
    "-unchecked",
    "-encoding",
    "utf8",
    "-Ywarn-dead-code",
    "-Ywarn-nullary-unit",
    "-Ywarn-unused",
    "-Ywarn-unused-import",
  ),
  resolvers ++= Seq(
    Resolver.mavenLocal,
    Resolver.typesafeRepo("releases"),
    Resolver.sonatypeRepo("releases"),
  ),
  testOptions in Test += Tests.Argument(TestFrameworks.ScalaTest, "-u", "target/test-reports", "-oD")
)

lazy val assemblySettings = Seq(
  test in assembly := {},
  assemblyJarName in assembly := name.value + ".jar",
  assemblyMergeStrategy in assembly := {
    case PathList("META-INF", xs @ _*) =>
      (xs map {_.toLowerCase}) match {
        case ("manifest.mf" :: Nil) | ("index.list" :: Nil) | ("dependencies" :: Nil) =>
          MergeStrategy.discard
        case _ =>
          MergeStrategy.first
      }
    case PathList("reference.conf") | PathList("application.conf") => MergeStrategy.concat
    case "dev.conf" | "test.conf" => MergeStrategy.discard
    case _ => MergeStrategy.first
  }
)

lazy val root = project
  .in(file("."))
  .disablePlugins(AssemblyPlugin)
  .settings(settings)
  .aggregate(googleApi)

lazy val googleApi = project
  .in(file("google-api"))
  .settings(
    settings,
    assemblySettings,
    libraryDependencies ++= Seq("com.google.api-ads" % "google-ads" % "3.0.1"),
  )
