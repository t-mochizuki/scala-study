package example

import java.net.URL
import java.io.{IOException, FileNotFoundException, FileOutputStream}

object Main extends App {
  args match {
    case Array(urlStr) => download(urlStr)
    case Array() => println("An argument is required")
  }

  def download(urlStr: String) {
    val url = new URL(urlStr)
    val conn = url.openConnection()

    try {

      val inputStream = conn.getInputStream()

      val bytes =
        try {
          inputStream.readAllBytes
        } finally {
          inputStream.close()
        }

      val fos = new FileOutputStream("test.png")
      try {
        fos.write(bytes)
      } finally {
        fos.close()
      }

    } catch {
      case e: FileNotFoundException =>
        println(s"${e.getMessage} is not found")
        throw e
      case e: IOException =>
        println(e.getMessage)
        throw e
    }

  }
}

