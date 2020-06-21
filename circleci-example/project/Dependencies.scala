import sbt._

object Dependencies {

  val scalatest = "org.scalatest" %% "scalatest" % "3.0.8" % Test

  val mysqlVersion = "8.0.12"
  val mysql = "mysql" % "mysql-connector-java" % mysqlVersion

  val scalikejdbcVersion = "3.4.2"
  val scalikejdbc = "org.scalikejdbc" %% "scalikejdbc" % scalikejdbcVersion
  val scalikejdbcConfig = "org.scalikejdbc" %% "scalikejdbc-config" % scalikejdbcVersion
  val scalikejdbcSyntaxSupportMacro =
    "org.scalikejdbc" %% "scalikejdbc-syntax-support-macro" % scalikejdbcVersion
  val scalikejdbcTest = "org.scalikejdbc" %% "scalikejdbc-test" % scalikejdbcVersion % Test

  val slf4jVersion = "1.7.30"
  val slf4jApi = "org.slf4j" % "slf4j-api" % slf4jVersion

  val logbackVersion = "1.2.3"
  val logbackCore = "ch.qos.logback" % "logback-core" % logbackVersion
  val logbackClassic = "ch.qos.logback" % "logback-classic" % logbackVersion

  val configVersion = "1.4.0"
  val config = "com.typesafe" % "config" % configVersion

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
