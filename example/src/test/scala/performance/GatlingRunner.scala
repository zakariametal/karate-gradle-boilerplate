package performance;

import com.intuit.karate.gatling.KarateProtocol
import com.intuit.karate.gatling.PreDef._
import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.core.structure.PopulationBuilder
import org.slf4j.{Logger, LoggerFactory}

import scala.collection.JavaConverters._
import scala.collection.mutable
import scala.concurrent.duration._
import scala.language.postfixOps

class GatlingRunner extends Simulation {

  val log: Logger = LoggerFactory.getLogger(classOf[GatlingRunner])
  val properties: mutable.Map[String, String] = System.getProperties.asScala
  
  val users: Int = properties.getOrElse("SIM_USERS", "10").asInstanceOf[String].toInt
  val period: Int = properties.getOrElse("SIM_PERIOD", "10").asInstanceOf[String].toInt
  val karateFeatureFiles: List[String] = properties.getOrElse("SIM_FEATURE", "automation/example.feature").split(",").map(_.trim).toList

  val protocol: KarateProtocol = karateProtocol()

  protocol.nameResolver = (req, ctx) => req.getHeader("karate-name")

  val scenarios: List[PopulationBuilder] = karateFeatureFiles.map(karateFeatureFile => {
    val create: ScenarioBuilder = scenario(karateFeatureFile).exec(karateFeature(s"classpath:$karateFeatureFile"))
    log.info("Running simulation of feature [{}] with [{}] users in [{}]", karateFeatureFile, users.toString, period.toString)
    create.inject(constantConcurrentUsers(users) during (period seconds)).protocols(protocol)
  })
  
  setUp(
    scenarios
  )
}
