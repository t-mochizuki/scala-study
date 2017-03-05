import com.github.tototoshi.csv._
import java.io.File

object HelloCsv extends App {
  val reader = CSVReader.open(new File(getClass.getResource("/animal.csv").getPath()))
  val content = reader.all()
  println(content)
  reader.close()

  val reader1 = CSVReader.open(new File(getClass.getResource("/with-header.csv").getPath()))
  val content1 = reader1.allWithHeaders()
  println(content1)
  Console println content1.map(m => m("Foo") + m("Baz"))
  reader1.close()

  val prefix = "hello-csv"
  val suffix = ".tmp"
  val tempFile = File.createTempFile(prefix, suffix)
  println(tempFile)
  tempFile.deleteOnExit()

  val writer = CSVWriter.open(tempFile)
  writer.writeAll(List(List("a", "b", "c"), List("d", "e", "f")))
  writer.close()

  val prefix1 = "hello-csv1"
  val tempFile1 = File.createTempFile(prefix1, suffix)
  println(tempFile1)
  tempFile1.deleteOnExit()

  val writer1 = CSVWriter.open(tempFile1)
  writer1.writeRow(List("1", "2", "3"))
  writer1.writeRow(List("4", "5", "6"))
  writer1.close()
}
