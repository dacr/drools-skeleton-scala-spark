package dummy

import org.kie.api._
import org.slf4j.LoggerFactory
import scala.collection.JavaConverters._

object Dummy {
  val logger = LoggerFactory.getLogger(Dummy.getClass())

  def main(args: Array[String]): Unit = {
    val kServices = KieServices.Factory.get
    val kContainer = kServices.getKieClasspathContainer()
    val kbase = kContainer.getKieBase("HelloKB")
    val ksession = kbase.newKieSession
    try {
      ksession.setGlobal("logger", LoggerFactory.getLogger("HelloKB"))
      ksession.setGlobal("gate", SparkGate())
      ksession.insert(Hello("world"))
      ksession.fireAllRules()
      val messages = ksession.getObjects().asScala.collect { case HelloResponse(msg) => msg }
      assert(messages.toList == List("Hello world"))
      val strings = ksession.getObjects().asScala.collect { case s:String => s }
      assert(strings.toList == List("hello world"))
      val  doubles = ksession.getObjects().asScala.collect { case d:Double => d }
      assert(doubles.headOption == Some(5150d))

    } finally {
      ksession.dispose()
    }
  }
}
