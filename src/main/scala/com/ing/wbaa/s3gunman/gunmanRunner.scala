package com.ing.wbaa.s3gunman

import io.gatling.app.Gatling
import io.gatling.core.config.GatlingPropertiesBuilder

object gunmanRunner {
  def main(args: Array[String]): Unit = {

    val props = new GatlingPropertiesBuilder
    props.simulationClass(classOf[testSimulation].getName)
    Gatling.fromMap(props.build)
  }
}
