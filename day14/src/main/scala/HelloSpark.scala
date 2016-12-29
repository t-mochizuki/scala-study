import org.apache.spark.SparkContext
import org.apache.spark.SparkConf

object HelloSpark extends App {
  val appName = "HelloSpark"
  val master = "local"
  val sparkConf = new SparkConf().setAppName(appName).setMaster(master)
  val sparkContext = new SparkContext(sparkConf)
  val textFile = sparkContext.textFile(getClass.getResource("/sample.txt").getPath())
  // PairRDDFunctions#reduceByKey
  val counts1 = textFile.flatMap(line => line.split(" ")).map(word => (word, 1)).reduceByKey(_ + _)
  // PairRDDFunctions#countByKey
  val counts2 = textFile.flatMap(line => line.split(" ")).map(word => (word, 1)).countByKey
  // RDD#collect
  Console println counts1.collect().toMap == counts2
  sparkContext.stop()
}
