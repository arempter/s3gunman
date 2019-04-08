package com.ing.wbaa.s3gunman.action

import com.amazonaws.ClientConfiguration
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain
import com.amazonaws.client.builder.AwsClientBuilder
import com.amazonaws.services.s3.transfer.{TransferManager, TransferManagerBuilder}
import com.amazonaws.services.s3.{AmazonS3, AmazonS3ClientBuilder}
import com.ing.wbaa.s3gunman.protocol.S3Protocol

object Aws {
    val cliConf = new ClientConfiguration()
    cliConf.setSignerOverride("S3SignerType")

  val s3Client: S3Protocol => AmazonS3 = { protocol =>
    val client: AmazonS3ClientBuilder = AmazonS3ClientBuilder
      .standard()
      .withCredentials(new DefaultAWSCredentialsProviderChain())
      .withClientConfiguration(cliConf)
      .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(protocol.endpoint, protocol.region));

    client.setPathStyleAccessEnabled(true)
    client.build()
  }

  val transferMgr: S3Protocol => TransferManager = protocol => TransferManagerBuilder.standard().withS3Client(s3Client(protocol)).build()
}
