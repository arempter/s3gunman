package com.ing.wbaa.s3gunman

import com.ing.wbaa.s3gunman.action.{DeleteActionBuilder, PutActionBuilder, MultipartUploadActionBuilder}
import com.ing.wbaa.s3gunman.protocol.S3Protocol
import io.gatling.core.Predef._

import scala.concurrent.duration._

class testSimulation extends Simulation {

  import com.ing.wbaa.s3gunman.awsSettings._
  import com.ing.wbaa.s3gunman.gatlingScenarioSettings._

  val s3Protocol = S3Protocol(awsEndpoint, awsRegion, awsBucket, awsPath, file4Upload)
  val s3upload = scenario("Test AWS upload").exec(new PutActionBuilder)
  val s3MultiUpload = scenario("Test AWS multipart delete").exec(new MultipartUploadActionBuilder)
  val s3delete = scenario("Test AWS delete").exec(new DeleteActionBuilder)

  if (singlePutEnabled) {
    setUp(
      s3upload.inject(rampUsers(noOfUsers) during (testDuration seconds)).protocols(s3Protocol),
      s3delete.inject(rampUsers(noOfUsers - 2) during (testDuration + 10 seconds)).protocols(s3Protocol)
    )
  } else if (multipartEnabled && !file4Upload.isEmpty) {
    setUp(
      s3MultiUpload.inject(rampUsers(noOfUsers) during (testDuration seconds)).protocols(s3Protocol),
      s3delete.inject(rampUsers(noOfUsers - 2) during (testDuration + 10 seconds)).protocols(s3Protocol)
    )
  }

}
