package com.ing.wbaa.s3gunman

import com.ing.wbaa.s3gunman.action.{DeleteActionBuilder, MultipartDownloadActionBuilder, MultipartUploadActionBuilder, PutActionBuilder}
import com.ing.wbaa.s3gunman.protocol.S3Protocol
import io.gatling.core.Predef._

import scala.concurrent.duration._

class testSimulation extends Simulation {

  import com.ing.wbaa.s3gunman.awsSettings._
  import com.ing.wbaa.s3gunman.gatlingScenarioSettings._

  val s3Protocol = S3Protocol(awsEndpoint, awsRegion, awsBucket, awsPath, file4Upload)
  val s3upload = scenario("Test AWS upload").repeat(repeatScenario) { exec(new PutActionBuilder) }
  val s3MultiUpload = scenario("Test AWS multipart upload").repeat(repeatScenario) { exec(new MultipartUploadActionBuilder) }
  val s3MultiDownload = scenario("Test AWS multipart download").repeat(repeatScenario) { exec(new MultipartDownloadActionBuilder) }
  val s3delete = scenario("Test AWS delete").exec(new DeleteActionBuilder)

  if (singlePutEnabled && !multipartEnabled) {
    setUp(
      s3upload.inject(
        rampUsers(noOfUsers) during (testDuration seconds)).protocols(s3Protocol),
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
          constantUsersPerSec(noOfUsers / 4) during (testDuration seconds)).protocols(s3Protocol),
        s3delete.inject(nothingFor(10), rampUsers(noOfUsers - 2) during (testDuration + 10 seconds)).protocols(s3Protocol)
      )
    } else {
      setUp(
        s3MultiUpload.inject(rampUsers(noOfUsers) during (testDuration seconds)).protocols(s3Protocol),
        s3delete.inject(nothingFor(10), rampUsers(noOfUsers - 2) during (testDuration + 10 seconds)).protocols(s3Protocol)
      )
    }
  } else if (multipartDownload && !multipartEnabled && !singlePutEnabled) {
    setUp(
      s3MultiDownload.inject(rampUsers(noOfUsers) during (testDuration seconds)).protocols(s3Protocol)
    )
  }

}
