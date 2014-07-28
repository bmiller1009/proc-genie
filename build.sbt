import de.johoop.cpd4sbt.CopyPasteDetector._
import de.johoop.findbugs4sbt.FindBugs._

name := "proc-genie"

organization := "org.bradfordmiller"

version := "0.1"

description := "A multi-lingual method generator for stored procedure calls"

scalaVersion := "2.10.4"

cpdSettings

findbugsSettings

libraryDependencies ++= Seq(
			"org.scalatest" % "scalatest_2.10" % "2.0" % "test",
			"mysql" % "mysql-connector-java" % "5.1.30",
			"org.slf4j" % "slf4j-api" % "1.7.7",
			"org.slf4j" % "slf4j-simple" % "1.7.7",
			"org.clapper" %% "grizzled-slf4j" % "1.0.1",
			"com.github.scopt" %% "scopt" % "3.2.0"
			)

seq(com.github.retronym.SbtOneJar.oneJarSettings: _*)

// ---------------------------------------------------------------------------
// Additional compiler options and plugins

scalacOptions ++= Seq(
  "-deprecation", "-unchecked", "-feature"
  )

// ---------------------------------------------------------------------------
// Publishing criteria
publishTo <<= version { v: String =>
  val nexus = "https://oss.sonatype.org/"
  if (v.trim.endsWith("SNAPSHOT"))
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases" at nexus + "service/local/staging/deploy/maven2")
}

publishMavenStyle := true

publishArtifact in Test := false

//pomIncludeRepository := { _ => true }