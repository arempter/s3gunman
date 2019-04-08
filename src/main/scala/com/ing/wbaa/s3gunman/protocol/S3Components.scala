package com.ing.wbaa.s3gunman.protocol

import io.gatling.core.protocol.ProtocolComponents
import io.gatling.core.session.Session

case class S3Components(protocol: S3Protocol) extends ProtocolComponents {
  override def onStart: Session => Session = ProtocolComponents.NoopOnStart

  override def onExit: Session => Unit = ProtocolComponents.NoopOnExit
}
