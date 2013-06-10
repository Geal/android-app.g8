import sbt._
import sbtandroid._

import Keys._
import AndroidKeys._

object General {
  val settings = Defaults.defaultSettings ++ Seq (
    name := "$name$",
    version := "0.1",
    versionCode := 0,
    scalaVersion := "$scala_version$",
    platformName in Android := "android-$api_level$",
    javacOptions ++= Seq("-encoding", "UTF-8", "-source", "1.6", "-target", "1.6")
  )

  val proguardSettings = Seq (
    useProguard in Android := $useProguard$
    , proguardOption in Android := "-keep class scala.collection.SeqLike { public protected *; }"
  )

  lazy val fullAndroidSettings =
    General.settings ++
    AndroidProject.androidSettings ++
    TypedResources.settings ++
    proguardSettings ++
    AndroidManifestGenerator.settings ++
    AndroidMarketPublish.settings ++ Seq (
      keyalias in Android := "change-me",
      libraryDependencies += "org.scalatest" %% "scalatest" % "$scalatest_version$" % "test"
    )
}

object AndroidBuild extends Build {
  lazy val main = Project (
    "$name$",
    file("."),
    settings = General.fullAndroidSettings
  )

  lazy val tests = Project (
    "tests",
    file("tests"),
    settings = General.settings ++
               AndroidTest.androidSettings ++
               General.proguardSettings ++ Seq (
      name := "$name$Tests"
    )
  ) dependsOn main
}
