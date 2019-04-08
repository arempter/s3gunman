package com.ing.wbaa.s3gunman

import com.typesafe.config.ConfigFactory

object awsSettings {
  val conf = ConfigFactory.load().getConfig("aws")
  val awsEndpoint = conf.getString("endpoint")
  val awsRegion = conf.getString("region")
  val awsBucket = conf.getString("bucket")
  val awsPath = conf.getString("prefix")
}

object gatlingScenarioSettings {
  val conf = ConfigFactory.load().getConfig("gatling")
  val noOfUsers = conf.getInt("users")
  val testDuration = conf.getInt("duration")
  val file4Upload = conf.getString("file4Upload")
  val singlePutEnabled = conf.getBoolean("singlePutEnabled")
  val multipartEnabled = conf.getBoolean("multipartEnabled")
}
