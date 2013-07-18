name := "betamax-scala"

version := "1.0"

scalaVersion := "2.10.2"

libraryDependencies ++= Seq(
  "co.freeside" % "betamax" % "1.1.2",
  "org.codehaus.groovy" % "groovy-all" % "1.8.8",
  "org.specs2" %% "specs2" % "2.1",
  "junit" % "junit" % "4.11",
  "org.scalatest" %% "scalatest" % "1.9.1" % "test"
)

