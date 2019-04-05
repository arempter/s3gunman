package com.ing.wbaa.s3gunman

import com.ing.wbaa.s3gunman.protocol.S3Protocol
import com.ing.wbaa.s3gunman.request.{DeleteActionBuilder, PutActionBuilder}
import io.gatling.core.Predef._

import scala.concurrent.duration._

class testSimulation extends Simulation {
  import com.ing.wbaa.s3gunman.awsSettings._
  import com.ing.wbaa.s3gunman.gatlingScenarioSettings._

  val s3Protocol = S3Protocol(awsAccessKey, awsSecretKey, awsSessionToken, awsEndpoint, awsRegion, awsBucket)
  val s3upload = scenario("Test AWS upload").exec(new PutActionBuilder)
  val s3delete = scenario("Test AWS delete").exec(new DeleteActionBuilder)

  setUp(
    s3upload.inject(rampUsers(noOfUsers) over (testDuration seconds)).protocols(s3Protocol),
    s3delete.inject(rampUsers(noOfUsers-2) over (testDuration+10 seconds)).protocols(s3Protocol)
  )

}
