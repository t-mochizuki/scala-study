package example

import ApplicationConf._

object Main {
  def main(args: Array[String]): Unit = {
    println(s"host=${apiServer.host}")
    println(s"port=${apiServer.port}")
  }
}
