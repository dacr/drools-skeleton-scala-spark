package dummy

import org.kie.api._
import org.slf4j.LoggerFactory
import scala.jdk.CollectionConverters._

object Dummy {
  val logger = LoggerFactory.getLogger(Dummy.getClass())

  def main(args: Array[String]): Unit = {
    val kServices = KieServices.Factory.get
    val kContainer = kServices.getKieClasspathContainer()
    val kbase = kContainer.getKieBase("HelloKB")
    val ksession = kbase.newKieSession
    try {
      ksession.setGlobal("logger", LoggerFactory.getLogger("HelloKB"))
      ksession.insert(Hello("world"))
      ksession.fireAllRules()
      val messages = ksession.getObjects().asScala.collect { case HelloResponse(msg) => msg }
      assert(messages.toList == List("Hello world"))
    } finally {
      ksession.dispose()
    }
  }
}
