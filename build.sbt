name := "s3gunman"

version := "0.1"

scalaVersion := "2.12.8"

maintainer := "arempter@gmail.com"

val gatlingVer = "3.0.3"

libraryDependencies ++= Seq(
  "io.gatling" % "gatling-test-framework" % gatlingVer,
  "io.gatling.highcharts" % "gatling-charts-highcharts" % gatlingVer,
  "com.amazonaws" % "aws-java-sdk-s3" % "1.11.532"
)

enablePlugins(JavaAppPackaging)
