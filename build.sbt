// so we can use keywords from Android, such as 'Android' and 'proguardOptions'
import android.Keys._
 
// load the android plugin into the build
android.Plugin.androidBuild
 
// project name, completely optional
name := "Beer Festival Drink Helper"
 
// pick the version of scala you want to use
scalaVersion := "2.10.2"
 
// should probably make this default for compiling scala code on android
proguardOptions in Android ++= Seq("-dontobfuscate", "-dontoptimize")
 
// scala 2.10 flag for feature warnings
scalacOptions in Compile ++= Seq("-unchecked", "-deprecation", "-feature", "-language:implicitConversions", "source", "1.6", "target", "1.6")
 
// call install and run without having to prefix with android:
run <<= run in Android
 
install <<= install in Android

platformTarget in Android := "android-8"

libraryDependencies ++= Seq("org.scalatest" %% "scalatest" % "2.0.M5b" % "test", "xpp3" % "xpp3" % "1.1.4c" % "provided")
