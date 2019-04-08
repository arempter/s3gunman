package com.ing.wbaa.s3gunman.action

import com.amazonaws.services.s3.model.ObjectMetadata
import com.ing.wbaa.s3gunman.action.Aws._
import com.ing.wbaa.s3gunman.action.helpers.s3ObjectName
import com.ing.wbaa.s3gunman.protocol.S3Protocol
import io.gatling.commons.stats.{KO, OK}
import io.gatling.core.action.{Action, ChainableAction}
import io.gatling.core.session.Session
import io.gatling.core.stats.StatsEngine
import io.gatling.core.util.NameGen

import scala.util.{Failure, Success, Try}

class PutAction(protocol: S3Protocol, val statsEngine: StatsEngine, val next: Action) extends ChainableAction with NameGen {
  override def name: String = genName("S3Put")

  private def fileForUpload = getClass.getClassLoader.getResourceAsStream("file.dd")

  override def execute(session: Session): Unit = {
    val startTime = System.currentTimeMillis()

    Try {
      val metadata = new ObjectMetadata()
      s3Client(protocol)
        .putObject(protocol.bucket, s3ObjectName(protocol),
          fileForUpload, metadata)
    } match {
      case Success(uploadResult) =>
        val msg = "Upload ETAG ------>: " + uploadResult.getETag
        val endTime = System.currentTimeMillis()
        statsEngine.logResponse(session, name, startTime, endTime, OK, None, Some(msg))
        next ! session
      case Failure(exception) =>
        val endTime = System.currentTimeMillis()
        statsEngine.logResponse(session, name, startTime, endTime, KO, None, Some(exception.getMessage))
        next ! session
    }
  }

}