import sbt._
import Keys._

object Build extends Build {
  val ScalaVersion = "2.10.3"

  lazy val root = Project("clickstream_generator", file(".")) settings(
      version := "0.1",
      scalaVersion := ScalaVersion,
      organization := "com.cloudwick",
      scalacOptions ++= Seq("-unchecked", "-deprecation"),
      libraryDependencies ++= Dependencies.compile,
      libraryDependencies ++= Dependencies.testDependencies,
      resolvers ++= Dependencies.resolvers
    )

  object Dependencies {
    val compile = Seq(
      "org.slf4j" % "slf4j-api" % "1.7.5",
      "com.github.scopt" %% "scopt" % "3.1.0",
      "org.apache.avro" % "avro" % "1.7.6",
      "com.typesafe" % "config" % "1.2.0",
      "br.com.six2six" % "bfgex" % "1.0",
      "commons-lang" % "commons-lang" % "2.6",
      "org.apache.commons" % "commons-math" % "2.2"
    )

    val testDependencies = Seq(
      "org.specs2" %% "specs2" % "1.14" % "test",
      "org.mockito" % "mockito-all" % "1.9.0" % "test",
      "org.hamcrest" % "hamcrest-all" % "1.1" % "test"
    )

    val resolvers = Seq(
      "amateras-repo" at "http://amateras.sourceforge.jp/mvn/"
    )
  }
}
