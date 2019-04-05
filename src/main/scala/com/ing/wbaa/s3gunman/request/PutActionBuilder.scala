package com.ing.wbaa.s3gunman.request

import com.ing.wbaa.s3gunman.protocol.S3Protocol
import io.gatling.core.action.Action
import io.gatling.core.action.builder.ActionBuilder
import io.gatling.core.protocol.ProtocolComponentsRegistry
import io.gatling.core.structure.ScenarioContext

class PutActionBuilder extends ActionBuilder {
  private def components(protocolComponentsRegistry: ProtocolComponentsRegistry) =
    protocolComponentsRegistry.components(S3Protocol.s3ProtocolKey)

  override def build(ctx: ScenarioContext, next: Action): Action = {
    import ctx._
    val statsEngine = coreComponents.statsEngine
    val s3Components = components(protocolComponentsRegistry)
    new PutAction(s3Components.protocol, statsEngine, next)
  }

}
