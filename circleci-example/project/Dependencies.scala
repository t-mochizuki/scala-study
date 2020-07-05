import sbt._

object Dependencies {

  object Version {
    val akka = "2.6.6"
    val akkaHttp = "10.1.12"
    val circe = "0.13.0"
    val mysql = "8.0.12"
    val scalikejdbc = "3.4.2"
    val slf4j = "1.7.30"
    val logback = "1.2.3"
    val sangria = "2.0.0"
  }

  val akkaStream = "com.typesafe.akka" %% "akka-stream" % Version.akka

  val akkaHttp = "com.typesafe.akka" %% "akka-http" % Version.akkaHttp

  val akkaHttpCirce = "de.heikoseeberger" %% "akka-http-circe" % "1.32.0"

  val circeCore = "io.circe" %% "circe-core" % Version.circe
  val circeGeneric = "io.circe" %% "circe-generic" % Version.circe
  val circeParser = "io.circe" %% "circe-parser" % Version.circe

  val scalatest = "org.scalatest" %% "scalatest" % "3.0.8" % Test

  val mysql = "mysql" % "mysql-connector-java" % Version.mysql

  val scalikejdbc = "org.scalikejdbc" %% "scalikejdbc" % Version.scalikejdbc
  val scalikejdbcConfig = "org.scalikejdbc" %% "scalikejdbc-config" % Version.scalikejdbc
  val scalikejdbcSyntaxSupportMacro =
    "org.scalikejdbc" %% "scalikejdbc-syntax-support-macro" % Version.scalikejdbc
  val scalikejdbcTest = "org.scalikejdbc" %% "scalikejdbc-test" % Version.scalikejdbc % Test

  val slf4jApi = "org.slf4j" % "slf4j-api" % Version.slf4j

  val logbackCore = "ch.qos.logback" % "logback-core" % Version.logback
  val logbackClassic = "ch.qos.logback" % "logback-classic" % Version.logback

  val sangria = "org.sangria-graphql" %% "sangria" % Version.sangria

  val sangriaCirce = "org.sangria-graphql" %% "sangria-circe" % "1.3.0"

  val sangriaRelay = "org.sangria-graphql" %% "sangria-relay" % "2.0.0-M2"
  val jaxbApi = "javax.xml.bind" % "jaxb-api" % "2.3.1"

  val graphQLServerDeps = Seq(
    sangria,
    sangriaCirce,
    sangriaRelay,
    jaxbApi
  )

  val webServerDeps = Seq(
    akkaStream,
    akkaHttp,
    akkaHttpCirce,
    circeCore,
    circeGeneric,
    circeParser
  )

  val coreDeps = Seq(
    scalatest,
    mysql,
    scalikejdbc,
    scalikejdbcConfig,
    scalikejdbcSyntaxSupportMacro,
    scalikejdbcTest,
    slf4jApi,
    logbackCore,
    logbackClassic
  )

  val rootDeps = Seq(
    mysql
  )

}
