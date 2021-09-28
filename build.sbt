import Dependencies.Ver
import org.scalablytyped.converter.plugin.ScalablyTypedConverterGenSourcePlugin.autoImport.{SourceGenMode, stSourceGenMode}
import sbt.Keys.description
import sbtcrossproject.CrossPlugin.autoImport.{CrossType, crossProject}

scalaVersion := Ver.scala3

ThisBuild / homepage      := Some(url("https://github.com/banana-rdf/rdflib.types"))
ThisBuild / licenses      += ("MIT", url("https://opensource.org/licenses/Apache-2.0"))
ThisBuild / organization  := "org.w3"
ThisBuild / shellPrompt   := ((s: State) => Project.extract(s).currentRef.project + "> ")
ThisBuild / versionScheme := Some("early-semver")

val scala3jsOptions =  Seq(
	// "-classpath", "foo:bar:...",         // Add to the classpath.
	//"-encoding", "utf-8",                // Specify character encoding used by source files.
	"-deprecation",                      // Emit warning and location for usages of deprecated APIs.
	"-unchecked",                        // Enable additional warnings where generated code depends on assumptions.
	"-feature",                          // Emit warning and location for usages of features that should be imported explicitly.
	//"-explain",                          // Explain errors in more detail.
	//"-explain-types",                    // Explain type errors in more detail.
	"-indent",                           // Together with -rewrite, remove {...} syntax when possible due to significant indentation.
	// "-no-indent",                        // Require classical {...} syntax, indentation is not significant.
	"-new-syntax",                       // Require `then` and `do` in control expressions.
	// "-old-syntax",                       // Require `(...)` around conditions.
	// "-language:Scala2",                  // Compile Scala 2 code, highlight what needs updating
	//"-language:strictEquality",          // Require +derives Eql+ for using == or != comparisons
	// "-rewrite",                          // Attempt to fix code automatically. Use with -indent and ...-migration.
	// "-scalajs",                          // Compile in Scala.js mode (requires scalajs-library.jar on the classpath).
	"-source:future",                       // Choices: future and future-migration. I use this to force future deprecation warnings, etc.
	// "-Xfatal-warnings",                  // Fail on warnings, not just errors
	// "-Xmigration",                       // Warn about constructs whose behavior may have changed since version.
	// "-Ysafe-init",                       // Warn on field access before initialization
	"-Yexplicit-nulls"                  // For explicit nulls behavior.
)


lazy val commonSettings = Seq(
	name := "rdflib.types",
	version := "0.9-SNAPSHOT",
	description := "RDF framework for Scala",
	startYear := Some(2012),
	scalaVersion := Ver.scala3,
	updateOptions := updateOptions.value.withCachedResolution(true) //to speed up dependency resolution
)


lazy val rdflibTypes = project.in(file("."))
//	.enablePlugins(ScalaJSBundlerPlugin)
	//documentation here: https://scalablytyped.org/docs/library-developer
	// call stImport in sbt to generate new sources
	.enablePlugins(ScalablyTypedConverterGenSourcePlugin)
	.enablePlugins(ScalablyTypedConverterPlugin)
	.settings(commonSettings: _*)
	.settings(
		name := "rdflib-types",
		scalacOptions ++= scala3jsOptions,
		Compile / npmDependencies  += "rdflib" -> "2.2.7",
		useYarn := true,
		stUseScalaJsDom := true,
		stOutputPackage := "types",
		stMinimize := Selection.AllExcept("rdflib"),
//		stSourceGenMode := SourceGenMode.ResourceGenerator,
		stSourceGenMode := SourceGenMode.Manual( baseDirectory.value /"src"/"main"/"scala")
//		Test / classLoaderLayeringStrategy := ClassLoaderLayeringStrategy.ScalaLibrary,
	)

//
//lazy val rdflibScratch =  project.in(file("."))
//	// .enablePlugins(ScalaJSBundlerPlugin)
//	//documentation here: https://scalablytyped.org/docs/library-developer
//	// call stImport in sbt to generate new sources
//	//.enablePlugins(ScalablyTypedConverterGenSourcePlugin)
//	.enablePlugins(ScalablyTypedConverterPlugin)
//	.settings(commonSettings: _*)
//	.settings(
//		name := "rdflib-scratch",
//		useYarn := true,
//		scalacOptions ++= scala3jsOptions,
//		scalaJSUseMainModuleInitializer := true,
//		Compile / npmDependencies += "rdflib" -> "2.2.7",
//		stUseScalaJsDom := true,
//		libraryDependencies += "org.scala-js" %%% "rdflib-types" % "0.9-SNAPSHOT",
//		Compile / mainClass := Some( "org.w3.banana.testRdfLib" )
//	)
//

