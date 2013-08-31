import sbt._

import Keys._
//import sbtandroid._
//import sbtandroid.AndroidKeys._
//import sbtandroid.AndroidNdkKeys._
import AndroidKeys._
import AndroidNdkKeys._

object General {
  // Some basic configuration
  val settings = Defaults.defaultSettings ++ Seq (
    name := "Android Beer Festival Client",
    version := "0.2-SNAPSHOT",
    versionCode := 0,
    scalaVersion := "2.10.1",
    platformName in Android := "android-8",
    scalacOptions := Seq("-unchecked", "-deprecation", "-feature", "-language:implicitConversions", "source", "1.6", "target", "1.6")
  )

  val keptClasses = Seq("scala.Function1", "scala.Tuple2", "scala.collection.Seq", "scala.Option", "scala.Function2", "scala.collection.immutable.Map",
    "scala.collection.immutable.List", "scala.Enumeration$Value", "scala.collection.immutable.StringLike", "scala.reflect.ClassTag",
    "scala.runtime.DoubleRef", "scala.runtime.ObjectRef")

  // Default Proguard settings
  lazy val proguardSettings = inConfig(Android) (Seq (
    useProguard := true,
    proguardOptimizations += "-keep class uk.co.randomcoding.android.beerfestival.** { *; }",
    proguardOptimizations += "-keep class scala.collection.immutable.StringLike { *; }",
    proguardOption := "-keep class %s".format(keptClasses.mkString(", "))
  ))

  // Example NDK settings
  lazy val ndkSettings = AndroidNdk.settings ++ inConfig(Android) (Seq(
    jniClasses := Seq(),
    javahOutputFile := Some(new File("native.h"))
  ))

  // Full Android settings
  lazy val fullAndroidSettings =
    General.settings ++ Seq(
      libraryDependencies ++= Seq(
        "org.scalatest" %% "scalatest" % "2.0.M5b" % "test",
        "xpp3" % "xpp3" % "1.1.4c" % "provided"
      )
    )
    AndroidProject.androidSettings ++
    TypedResources.settings ++
    proguardSettings ++
    AndroidManifestGenerator.settings ++
    AndroidMarketPublish.settings ++ Seq (
      keyalias in Android := "change-me"
    )
}

object AndroidBuild extends Build {
  lazy val main = Project (
    "main",
    file("."),
    settings = General.fullAndroidSettings ++ AndroidEclipseDefaults.settings
  )

  lazy val tests = Project (
    "tests",
    file("tests"),
    settings = General.settings ++
               AndroidEclipseDefaults.settings ++
               AndroidTest.androidSettings ++
               General.proguardSettings ++ Seq (
      name := "Android Beer Festival ClientTests"
    )
  ) dependsOn main
}
