package com.ing.wbaa.s3gunman.request

import com.ing.wbaa.s3gunman.protocol.S3Protocol
import io.gatling.commons.stats.{KO, OK}
import io.gatling.core.action.{Action, ExitableAction}
import io.gatling.core.session.Session
import io.gatling.core.stats.StatsEngine
import io.gatling.core.stats.message.ResponseTimings
import io.gatling.core.util.NameGen
import com.ing.wbaa.s3gunman.request.Aws._

import scala.util.{Failure, Random, Success, Try}

class DeleteAction(protocol: S3Protocol, val statsEngine: StatsEngine, val next: Action) extends ExitableAction with NameGen {
  override def name: String = genName("S3Delete")

  override def execute(session: Session): Unit = {
    val rand = new Random()
    val startTime = System.currentTimeMillis()
    Try {
      s3Client(protocol).deleteObject(protocol.bucket, "gatlingTest-"+rand.nextInt(5))
    } match {
      case Success(_) =>
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
