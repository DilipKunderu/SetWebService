import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration._

object Add {
  val add = exec(http("Add")
    .post("/item/dilip"))
}

object Has {
  val has = exec(http("Has")
  .get("/item/dilip"))
}

object Delete {
  val delete = exec(http("Delete")
    .delete("/item/dilip"))
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

  val scn = scenario("Crud_Sequence").exec(Has.has, Add.add, Has.has, Delete.delete, Has.has)

  setUp(scn.inject(atOnceUsers(1)).protocols(httpProtocol))
}
