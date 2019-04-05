package com.ing.wbaa.s3gunman.request

import com.amazonaws.auth.{AWSCredentials, AWSStaticCredentialsProvider, BasicSessionCredentials}
import com.amazonaws.client.builder.AwsClientBuilder
import com.amazonaws.services.s3.{AmazonS3, AmazonS3ClientBuilder}
import com.ing.wbaa.s3gunman.protocol.S3Protocol

object Aws {

  val s3Client: S3Protocol => AmazonS3 = { protocol =>
    val credentials: AWSCredentials = new BasicSessionCredentials(protocol.awsAccessKey, protocol.awsSecretKey, protocol.awsSessionToken)

    val client: AmazonS3ClientBuilder = AmazonS3ClientBuilder
      .standard()
      .withCredentials(new AWSStaticCredentialsProvider(credentials))
      .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(protocol.endpoint, protocol.region));

    client.setPathStyleAccessEnabled(true)
    client.build()
  }
}
