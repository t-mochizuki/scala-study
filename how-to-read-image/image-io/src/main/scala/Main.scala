package example

import javax.imageio.ImageIO

object Main extends App {
  args match {
    case Array() => println("An argument is required")
    case arr => arr.foreach(download)
  }

  def download(urlStr: String) {

    val url = getClass.getClassLoader.getResource(urlStr)

    val inputStream = url.openConnection.getInputStream

    try {

      val imageInputStream = ImageIO.createImageInputStream(inputStream)

      try {

        val iteratorImageReader = ImageIO.getImageReaders(imageInputStream)

        if (iteratorImageReader.hasNext) {
          val imageReader = iteratorImageReader.next
          println(s"Format=${imageReader.getFormatName}")
        }

      } finally {
        imageInputStream.close()
      }

    } finally {
      inputStream.close()
    }

    val bufferedImage = ImageIO.read(url)

    println(s"Height=${bufferedImage.getHeight}")
    println(s"Width=${bufferedImage.getWidth}")

  }
}

