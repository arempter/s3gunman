package com.ing.wbaa.s3gunman.action

import com.ing.wbaa.s3gunman.protocol.S3Protocol
import io.gatling.core.action.Action
import io.gatling.core.action.builder.ActionBuilder
import io.gatling.core.protocol.ProtocolComponentsRegistry
import io.gatling.core.structure.ScenarioContext

class MultipartUploadActionBuilder extends ActionBuilder {
  private def components(protocolComponentsRegistry: ProtocolComponentsRegistry) =
    protocolComponentsRegistry.components(S3Protocol.s3ProtocolKey)

  override def build(ctx: ScenarioContext, next: Action): Action = {
    import ctx._
    val statsEngine = coreComponents.statsEngine
    val s3Components = components(protocolComponentsRegistry)
    new MultipartUploadAction(s3Components.protocol, statsEngine, next)
  }

}
