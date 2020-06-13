import sbt._

object Dependencies {

  val akkaVersion = "2.6.6"
  val akkaStream = "com.typesafe.akka" %% "akka-stream" % akkaVersion
  val akkaSlf4j = "com.typesafe.akka" %% "akka-slf4j" % akkaVersion
  val akkaTestkit = "com.typesafe.akka" %% "akka-testkit" % akkaVersion % Test

  val akkaHttpVersion = "10.1.12"
  val akkaHttp = "com.typesafe.akka" %% "akka-http" % akkaHttpVersion

  val akkaHttpCirce = "de.heikoseeberger" %% "akka-http-circe" % "1.32.0"

  val circeVersion = "0.13.0"
  val circeCore = "io.circe" %% "circe-core" % circeVersion
  val circeGeneric = "io.circe" %% "circe-generic" % circeVersion
  val circeParser = "io.circe" %% "circe-parser" % circeVersion

  val scalatest = "org.scalatest" %% "scalatest" % "3.0.8" % Test

  val postgresqlVersion = "42.2.13"
  val postgresql = "org.postgresql" % "postgresql" % postgresqlVersion

  val mysqlVersion = "8.0.20"
  val mysql = "mysql" % "mysql-connector-java" % mysqlVersion

  val scalikejdbcVersion = "3.4.2"
  val scalikejdbc = "org.scalikejdbc" %% "scalikejdbc" % scalikejdbcVersion
  val scalikejdbcConfig = "org.scalikejdbc" %% "scalikejdbc-config" % scalikejdbcVersion
  val scalikejdbcSyntaxSupportMacro = "org.scalikejdbc" %% "scalikejdbc-syntax-support-macro" % scalikejdbcVersion
  val scalikejdbcTest = "org.scalikejdbc" %% "scalikejdbc-test" % scalikejdbcVersion % Test

  val slf4jVersion = "1.7.30"
  val slf4jApi = "org.slf4j" % "slf4j-api" % slf4jVersion
  val logbackVersion = "1.2.3"
  val logbackCore = "ch.qos.logback" % "logback-core" % logbackVersion
  val logbackClassic = "ch.qos.logback" % "logback-classic" % logbackVersion

  val baseDeps = Seq(
    akkaStream,
    akkaSlf4j,
    akkaTestkit,
    akkaHttp,
    akkaHttpCirce,
    circeCore,
    circeGeneric,
    circeParser,
    scalatest,
    postgresql,
    scalikejdbc,
    scalikejdbcConfig,
    scalikejdbcSyntaxSupportMacro,
    scalikejdbcTest,
    slf4jApi,
    logbackCore,
    logbackClassic
  )

  val appDeps = Seq(
    mysql
  )

  val rootDeps = Seq(
    postgresql
  )

}
