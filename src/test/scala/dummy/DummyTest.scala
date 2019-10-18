package dummy

import scala.collection.JavaConverters._
import org.scalatest._, org.scalatest.OptionValues._
import org.slf4j.LoggerFactory
import org.kie.api._

class DummyTest extends FlatSpec with Matchers {

  "drools skeleton" should "support minimum viable knowledge base" in {
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
      messages.toList shouldBe List("Hello world")
      val strings = ksession.getObjects().asScala.collect { case s:String => s }
      strings.toList shouldBe List("hello world")
      val  doubles = ksession.getObjects().asScala.collect { case d:Double => d }
      doubles.headOption.value shouldBe 5150
    } finally {
      ksession.dispose()
    }
  }
}
