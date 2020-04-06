val http4sVersion = "0.20.20"

// Only necessary for SNAPSHOT releases
resolvers += Resolver.sonatypeRepo("snapshots")

libraryDependencies ++= Seq(
  "org.http4s" %% "http4s-dsl" % http4sVersion,
  "org.http4s" %% "http4s-blaze-server" % http4sVersion,
  "org.http4s" %% "http4s-blaze-client" % http4sVersion
)

val catsEffectVersion = "2.1.2"

libraryDependencies += "org.typelevel" %% "cats-effect" % catsEffectVersion

scalacOptions ++= Seq("-Ypartial-unification")

lazy val root = (project in file("."))
  .settings(
    name := "Example",
    scalaVersion := "2.12.11"
  )
