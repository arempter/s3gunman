package com.ing.wbaa.s3gunman.protocol

case class S3ProtocolBuilder(awsAccessKey: String, awsSecretKey: String, awsSessionToken: String, endpoint: String, region: String, bucket: String) {

  def build = S3Protocol(awsAccessKey, awsSecretKey, awsSessionToken, endpoint, region, bucket)

}
