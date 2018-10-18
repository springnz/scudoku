import com.typesafe.sbt.packager.universal.UniversalPlugin
import com.typesafe.sbt.packager.archetypes.JavaAppPackaging

ThisBuild / scalaVersion := "2.12.7"
ThisBuild / organization := "com.scudoku"
ThisBuild / fork := true
ThisBuild / scalacOptions ++= Seq(
  "-feature",
  "-language:reflectiveCalls" // For scallop subcommands
)
connectInput in run := true

maintainer in Universal := "peter@spring.co.nz"

enablePlugins(UniversalPlugin)
enablePlugins(JavaAppPackaging)

lazy val scudoku = (project in file("."))
  .settings(
    name := "Scudoku",
    libraryDependencies ++= Seq(
      "org.scalatest" %% "scalatest" % "3.0.5" % Test,
      "org.rogach" %% "scallop" % "3.1.3"
    )
  )


