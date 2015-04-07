lazy val root = (project in file(".")).
  settings(
    name := "monadzoo-persistent-view",
    version := "0.1",
    scalaVersion := "2.11.6",
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-actor" % "2.3.9",
      "com.typesafe.akka" %% "akka-persistence-experimental" % "2.3.9"
    )
  )
