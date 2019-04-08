package com.ing.wbaa.s3gunman.protocol

case class S3ProtocolBuilder(endpoint: String, region: String, bucket: String, prefix: String, file4upload: String) {

  def build = S3Protocol(endpoint, region, bucket, prefix, file4upload)

}
