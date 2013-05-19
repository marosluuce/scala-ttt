name := "Scala-TTT"

version := "0.1"

scalaVersion := "2.10.1"

scalacOptions ++= Seq("-unchecked", "-deprecation")

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "2.0.M5b" % "test",
)
