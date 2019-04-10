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
  val s3MultiUpload = scenario("Test AWS multipart upload").repeat(repeatScenario) { exec(new MultipartUploadActionBuilder) }
  val s3delete = scenario("Test AWS delete").exec(new DeleteActionBuilder)

  if (singlePutEnabled && !multipartEnabled) {
    setUp(
      s3upload.inject(
        rampUsers(noOfUsers) during (testDuration seconds),
        nothingFor(20),
        constantUsersPerSec(noOfUsers / 2) during (testDuration / 2)).protocols(s3Protocol),
      s3delete.inject(nothingFor(10), rampUsers(noOfUsers - 2) during (testDuration + 10 seconds)).protocols(s3Protocol)
    )
  } else if (multipartEnabled && !file4Upload.isEmpty) {
    if (multipartFlat && !multipartMixed) {
      setUp(
        s3MultiUpload.inject(constantUsersPerSec(noOfUsers) during (testDuration)).protocols(s3Protocol),
        s3delete.inject(nothingFor(10), rampUsers(noOfUsers - 2) during (testDuration + 10 seconds)).protocols(s3Protocol)
      )
    } else if (multipartMixed && !multipartFlat) {
      setUp(
        s3MultiUpload.inject(
          rampUsers(noOfUsers) during (testDuration seconds),
          nothingFor(20),
          constantUsersPerSec(noOfUsers / 4) during (testDuration)).protocols(s3Protocol),
        s3delete.inject(nothingFor(10), rampUsers(noOfUsers - 2) during (testDuration + 10 seconds)).protocols(s3Protocol)
      )
    } else {
      setUp(
        s3MultiUpload.inject(rampUsers(noOfUsers) during (testDuration seconds)).protocols(s3Protocol),
        s3delete.inject(nothingFor(10), rampUsers(noOfUsers - 2) during (testDuration + 10 seconds)).protocols(s3Protocol)
      )
    }
  }

}
