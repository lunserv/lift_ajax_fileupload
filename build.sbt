name := "fileupload"

version := "0.1-SNAPSHOT"

scalaVersion := "2.9.1"
 
seq(webSettings: _*)

// If using JRebel with 0.1.0 of the sbt web plugin
//jettyScanDirs := Nil
// using 0.2.4+ of the sbt web plugin
scanDirectories in Compile := Nil

//resolvers += "Java.net Maven2 Repository" at "http://download.java.net/maven/2/"

// you can also add multiple repositories at the same time
resolvers ++= Seq(
  "Scala Tools Releases" at "http://scala-tools.org/repo-releases/",
  "Java.net Maven2 Repository" at "http://download.java.net/maven/2/"
)

// if you have issues pulling dependencies from the scala-tools repositories (checksums don't match), you can disable checksums
//checksums := Nil

libraryDependencies ++= {
  val liftVersion = "2.4" // Put the current/latest lift version here
  Seq("net.liftweb" %% "lift-webkit" % liftVersion % "compile->default")
}

// when using the sbt web app plugin 0.2.4+, use "container" instead of "jetty" for the context
// Customize any further dependencies as desired
libraryDependencies ++= Seq(
  "org.eclipse.jetty" % "jetty-webapp" % "8.0.4.v20111024" % "container", // For Jetty 8
  "org.mortbay.jetty" % "jetty" % "6.1.22" % "test",
  "org.scala-tools.testing" % "specs_2.9.0" % "1.6.8" % "test", // For specs.org tests
  "junit" % "junit" % "4.8" % "test->default", // For JUnit 4 testing
  "javax.servlet" % "servlet-api" % "2.5" % "provided->default",
  "ch.qos.logback" % "logback-classic" % "0.9.26" % "compile->default" // Logging
)

//Casbah - mongo driver
libraryDependencies += "com.mongodb.casbah" %% "casbah" % "2.1.5-1"

