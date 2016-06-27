name := "project-b"

version := "1.0"

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  "com.typesafe" % "config" % "1.3.0",
  "org.scalatest" % "scalatest_2.11" % "2.2.4" % "test"
)

lazy val a = ProjectRef(file("../projecta"), "projecta")

lazy val b = (project in file(".")).dependsOn(ClasspathDependency(a, Some("compile->compile;test->test")))
