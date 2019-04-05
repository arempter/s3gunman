package com.ing.wbaa.s3gunman

import com.typesafe.config.ConfigFactory

object awsSettings {
  val conf = ConfigFactory.load().getConfig("aws")
  val awsAccessKey = conf.getString("accessKey")
  val awsSecretKey = conf.getString("secretKey")
  val awsSessionToken = conf.getString("sessionToken")
  val awsEndpoint = conf.getString("endpoint")
  val awsRegion = conf.getString("region")
  val awsBucket = conf.getString("bucket")
}

object gatlingScenarioSettings {
  val conf = ConfigFactory.load().getConfig("gatling")
  val noOfUsers = conf.getInt("users")
  val testDuration = conf.getInt("duration")
}
