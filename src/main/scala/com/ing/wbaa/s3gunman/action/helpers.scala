package com.ing.wbaa.s3gunman.action

import scala.util.Random

import com.ing.wbaa.s3gunman.protocol.S3Protocol

object helpers {
  private val rand = new Random()

  val s3ObjectName: S3Protocol => String = protocol =>
    if (!protocol.prefix.isEmpty)
      s"${protocol.prefix}/gatlingTest-${rand.nextInt(20)}"
    else
      s"gatlingTest-${rand.nextInt(20)}"

}
