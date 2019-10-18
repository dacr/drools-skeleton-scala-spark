package dummy

import org.apache.spark.sql.SparkSession

class SparkGate {
  val spark =
    SparkSession.builder()
      .master("local[*]")
      .getOrCreate()

  def sc = spark.sparkContext

  def hello():String = "hello world"

  def someSparkCompute():Double = {
    val rdd = sc.parallelize(1 to 100, 10)
    val n = rdd.map(_ + 1).sum()
    n
  }
}

object SparkGate {
  def apply(): SparkGate = new SparkGate()
}
