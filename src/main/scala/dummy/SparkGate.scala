package dummy

class SparkGate {
  def hello():String = "hello world"
}

object SparkGate {
  def apply(): SparkGate = new SparkGate()
}
