package com.ing.wbaa.s3gunman.protocol

import io.gatling.core.CoreComponents
import io.gatling.core.config.GatlingConfiguration
import io.gatling.core.protocol.{Protocol, ProtocolKey}

case class S3Protocol(endpoint: String, region: String, bucket: String, prefix: String, file4upload: String) extends Protocol {
 type Components = S3Components
}

object S3Protocol {
  def apply(endpoint: String, region: String, bucket: String, prefix: String, file4upload: String): S3Protocol =
    new S3Protocol(endpoint, region, bucket, prefix, file4upload)

  val s3ProtocolKey = new ProtocolKey[S3Protocol, S3Components] {

    type Protocol = S3Protocol
    type Components = S3Components

    override def protocolClass: Class[io.gatling.core.protocol.Protocol] = classOf[S3Protocol].asInstanceOf[Class[io.gatling.core.protocol.Protocol]]

    override def defaultProtocolValue(configuration: GatlingConfiguration): S3Protocol = throw new Exception("No default protocol configuration")

    override def newComponents(coreComponents: CoreComponents): S3Protocol => S3Components = { protocol => S3Components(protocol) }

  }
}
