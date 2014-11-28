import com.github.play2war.plugin._

name := """test-deploy"""

version := "1.0-SNAPSHOT"

Play2WarPlugin.play2WarSettings

Play2WarKeys.servletVersion := "3.0"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

resolvers += Resolver.sonatypeRepo("snapshots")

scalaVersion := "2.11.1"

libraryDependencies ++= Seq(
  "ws.securesocial" %% "securesocial" % "master-SNAPSHOT"
)

libraryDependencies ++= Seq(
  "log4j" % "log4j" % "1.2.14",
  "mysql" % "mysql-connector-java" % "5.1.34",
  "org.json" % "json" % "20141113",
  javaJdbc,
  javaEbean,
  cache,
  javaWs
)

