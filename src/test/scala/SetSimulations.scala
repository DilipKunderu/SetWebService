import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration._

object Add {

  val feeder = csv("strings.csv").random
  val add = feed(feeder).exec(http("Add")
    .post("/item/${Namesu}"))
    .pause(7)
}

object Has {
  val feeder = csv("strings.csv").random
  val has = feed(feeder).exec(http("Has")
  .get("/item/${Namesu}"))
    .pause(5)
}

object Delete {
  val feeder = csv("strings.csv").random
  val delete = feed(feeder).exec(http("Delete")
    .delete("/item/${Namesu}"))
    .pause(6)
}

class SetSimulations extends Simulation {
  val httpProtocol = http
    .baseUrl("http://localhost:8080")
    .proxy(Proxy("127.0.0.1", 8080).httpsPort(8001))
    .inferHtmlResources()
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
    .acceptEncodingHeader("gzip, deflate")
    .acceptLanguageHeader("en-US,en;q=0.5")
    .upgradeInsecureRequestsHeader("1")
    .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.14; rv:68.0) Gecko/20100101 Firefox/68.0")

  val users = scenario("Users").exec(Has.has, Add.add, Has.has)
  val admins = scenario("Admins").exec(Has.has, Add.add, Has.has, Delete.delete, Has.has)

  setUp(
    users.inject(rampUsers(10) during(10 seconds)),
      admins.inject(rampUsers(2) during (10 seconds))).protocols(httpProtocol)
}
