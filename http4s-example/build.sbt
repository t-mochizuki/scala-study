val http4sVersion = "0.20.20"
val catsEffectVersion = "2.1.2"
val logbackVersion = "1.2.3"

// Only necessary for SNAPSHOT releases
resolvers += Resolver.sonatypeRepo("snapshots")

libraryDependencies ++= Seq(
  "org.http4s" %% "http4s-circe" % http4sVersion,
  "org.http4s" %% "http4s-dsl" % http4sVersion,
  "org.http4s" %% "http4s-blaze-server" % http4sVersion,
  "org.http4s" %% "http4s-blaze-client" % http4sVersion,
  "org.typelevel" %% "cats-effect" % catsEffectVersion,
  "ch.qos.logback" % "logback-classic" % logbackVersion
)

scalacOptions ++= Seq("-Ypartial-unification")

lazy val root = (project in file("."))
  .settings(
    name := "Example",
    scalaVersion := "2.12.11"
  )
