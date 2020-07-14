import sbt._

object Dependencies {

  val config = "com.typesafe" % "config" % "1.4.0"
  val unboundidLdapsdk = "com.unboundid" % "unboundid-ldapsdk" % "5.1.0"

  val coreDeps = Seq(
    config,
    unboundidLdapsdk
  )

  val rootDeps = Nil

}
