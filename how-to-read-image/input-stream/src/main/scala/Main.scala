package example

import java.net.URL
import java.io.{IOException, FileNotFoundException, FileOutputStream}

object Main extends App {
  args match {
    case Array() => println("An argument is required")
    case arr => arr.foreach(download)
  }

  def download(urlStr: String) {

    val url = getClass.getClassLoader.getResource(urlStr)

    try {

      val inputStream = url.openConnection().getInputStream()

      val bytes =
        try {
          inputStream.readAllBytes
        } finally {
          inputStream.close()
        }

      val imageName = urlStr.split("/").last

      val fos = new FileOutputStream(imageName)
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

