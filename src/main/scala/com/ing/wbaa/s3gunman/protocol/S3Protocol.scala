package com.ing.wbaa.s3gunman.protocol

import akka.actor.ActorSystem
import io.gatling.core.CoreComponents
import io.gatling.core.config.GatlingConfiguration
import io.gatling.core.protocol.{Protocol, ProtocolKey}

case class S3Protocol(awsAccessKey: String, awsSecretKey: String, awsSessionToken: String, endpoint: String, region: String, bucket: String) extends Protocol {
 type Components = S3Components
}

object S3Protocol {
  def apply(awsAccessKey: String, awsSecretKey: String, awsSessionToken: String, endpoint: String, region: String, bucket: String): S3Protocol =
    new S3Protocol(awsAccessKey, awsSecretKey, awsSessionToken, endpoint, region, bucket)

  val s3ProtocolKey = new ProtocolKey {

    type Protocol = S3Protocol
    type Components = S3Components

    override def protocolClass: Class[io.gatling.core.protocol.Protocol] = classOf[S3Protocol].asInstanceOf[Class[io.gatling.core.protocol.Protocol]]

    override def defaultProtocolValue(configuration: GatlingConfiguration): S3Protocol = throw new Exception("no default protocol vaule")

    override def newComponents(system: ActorSystem, coreComponents: CoreComponents): S3Protocol => S3Components = { protocol => S3Components(protocol) }
  }
}
