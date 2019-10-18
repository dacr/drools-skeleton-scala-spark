name := "drools-skeleton-scala-spark"
version := "1"
scalaVersion := "2.12.10"

lazy val versions = new {
  val drools    = "7.28.0.Final"
  val logback   = "1.2.3"
  val scalatest = "3.0.8"
  val spark     = "2.4.4"
}

libraryDependencies ++= Seq(
    "drools-compiler",
    "drools-core"
).map("org.drools" % _ % versions.drools)

libraryDependencies ++= Seq(
  "org.apache.spark"        %% "spark-sql"       % versions.spark,
  "ch.qos.logback"           % "logback-classic" % versions.logback,
  "org.scalatest"           %% "scalatest"       % versions.scalatest % "test"
)
