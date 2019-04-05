package com.ing.wbaa.s3gunman.request

import java.util.Random

import com.amazonaws.services.s3.model.ObjectMetadata
import com.ing.wbaa.s3gunman.protocol.S3Protocol
import com.ing.wbaa.s3gunman.request.Aws._
import io.gatling.commons.stats.{KO, OK}
import io.gatling.core.action.{Action, ExitableAction}
import io.gatling.core.session.Session
import io.gatling.core.stats.StatsEngine
import io.gatling.core.stats.message.ResponseTimings
import io.gatling.core.util.NameGen

import scala.util.{Failure, Success, Try}

class PutAction(protocol: S3Protocol, val statsEngine: StatsEngine, val next: Action) extends ExitableAction with NameGen {
  override def name: String = genName("S3Put")

  private def fileForUpload = getClass.getClassLoader.getResourceAsStream("file.dd")

  override def execute(session: Session): Unit = {
    val rand = new Random()
    val startTime = System.currentTimeMillis()
    Try {
      val metatdata = new ObjectMetadata()
      s3Client(protocol)
        .putObject(protocol.bucket, "gatlingTest-"+rand.nextInt(5),
          fileForUpload, metatdata)
    } match {
      case Success(uploadResult) =>
        println("Debug, upload ETAG ------>: " + uploadResult.getETag)
        val endTime = System.currentTimeMillis()
        val responseTimings = ResponseTimings(startTime, endTime)
        statsEngine.logResponse(session, name, responseTimings, OK, None, None)
        next ! session
      case Failure(exception) =>
        val endTime = System.currentTimeMillis()
        val responseTimings = ResponseTimings(startTime, endTime)
        statsEngine.logResponse(session, name, responseTimings, KO, None, Some(exception.getMessage))
        next ! session
    }
  }

}
