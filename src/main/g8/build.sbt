// so we can use keywords from Android, such as 'Android' and 'proguardOptions'
import android.Keys._
 
// load the android plugin into the build
android.Plugin.androidBuild
 
// project name, completely optional
name := "$name$"
 
// pick the version of scala you want to use
scalaVersion := "$scala_version$"
 
// scala 2.10 flag for feature warnings
scalacOptions in Compile += "-feature"
 
// for non-ant-based projects, you'll need this for the specific build target:
platformTarget in Android := "android-$api_level$"
 
proguardCache in Android ++= Seq(
  ProguardCache("org.scaloid") % "org.scaloid"
)

proguardOptions in Android ++= Seq("-dontobfuscate", "-dontoptimize", "-dontwarn scala.collection.mutable.**", "-keep class scala.collection.SeqLike { public protected *; }")

libraryDependencies ++= Seq("org.scaloid" %% "scaloid" % "3.3-8",
                            "org.scalatest" %% "scalatest" % "$scalatest_version$" % "test")

scalacOptions in Compile += "-feature"

// call install and run without having to prefix with android:
run <<= run in Android
 
install <<= install in Android
