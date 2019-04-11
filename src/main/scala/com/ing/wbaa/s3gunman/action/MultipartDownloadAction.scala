package com.ing.wbaa.s3gunman.action

import java.io.File

import com.amazonaws.services.s3.transfer.Download
import com.ing.wbaa.s3gunman.action.Aws.{s3Client, transferMgr}
import com.ing.wbaa.s3gunman.protocol.S3Protocol
import io.gatling.commons.stats.{KO, OK}
import io.gatling.core.action.{Action, ChainableAction}
import io.gatling.core.session.Session
import io.gatling.core.stats.StatsEngine
import io.gatling.core.util.NameGen

import scala.util.{Failure, Random, Success, Try}

class MultipartDownloadAction(protocol: S3Protocol, val statsEngine: StatsEngine, val next: Action) extends ChainableAction with NameGen {
  override def name: String = genName("S3Multipart-download")

  override def execute(session: Session): Unit = {
    def progressHelper(s3object: String, download: Download) = {
      println(s"$s3object, percent complete: ${download.getProgress.getPercentTransferred} bytesTransfered: ${download.getProgress.getBytesTransferred}")
    }

    def downloadHelper(s3object: String)  = {
      val download = transferMgr(protocol).download(protocol.bucket, s3object, new File("/dev/null"))
      progressHelper(s3object, download)
      download.waitForCompletion()
      println(s"Download of $s3object completed")
      transferMgr(protocol).shutdownNow()
    }
    val startTime = System.currentTimeMillis()

    Try {
      val objects = s3Client(protocol).listObjects(protocol.bucket, protocol.prefix).getObjectSummaries
      val random = new Random()
      downloadHelper(objects.get(random.nextInt(objects.size())).getKey)
    } match {
      case Success(_) =>
        val endTime = System.currentTimeMillis()
        statsEngine.logResponse(session, name, startTime, endTime, OK, None, None)
        next ! session
      case Failure(exception) =>
        val endTime = System.currentTimeMillis()
        statsEngine.logResponse(session, name, startTime, endTime, KO, None, Some(exception.getMessage))
        next ! session
    }
  }
}