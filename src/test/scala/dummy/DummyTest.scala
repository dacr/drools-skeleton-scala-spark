package dummy

import scala.jdk.CollectionConverters._
import org.scalatest._
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
      ksession.insert(Hello("world"))
      ksession.fireAllRules()
      val messages = ksession.getObjects().asScala.collect { case HelloResponse(msg) => msg }
      messages.toList shouldBe List("Hello world")
    } finally {
      ksession.dispose()
    }
  }
}
