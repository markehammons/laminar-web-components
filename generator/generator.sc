import $file.material
import $file.builder
import ammonite.ops._

val organization = "com.github.uosis"
val publishTo = builder.GitHubRepository("uosis", "laminar-web-components")

val outputProjectPath = os.pwd / "material"

val buildSbt = new builder.SBTProjectBuilder(
  material.componentCollection,
  organization,
  "0.11.0",
  publishTo
).build
val code = new builder.CollectionBuilder(
  material.componentCollection,
  organization
).build
def generateMaterial(outputProjectPath: Path = outputProjectPath) = {

  val srcDir = outputProjectPath / "src" / "main" / "scala"
  val projectDir = outputProjectPath / "project"
  mkdir(srcDir)
  mkdir(projectDir)
  os.write.over(
    projectDir / "plugins.sbt",
    """
    |addSbtPlugin("ch.epfl.scala" % "sbt-scalajs-bundler" % "0.20.0")
    |addSbtPlugin("org.scala-js" % "sbt-scalajs" % "1.7.0")
    |addSbtPlugin("com.codecommit" % "sbt-github-packages" % "0.5.2")
    """.stripMargin
  )

  os.write.over(
    srcDir / "material.scala",
    code
  )
  os.write.over(outputProjectPath / "build.sbt", buildSbt)

  outputProjectPath
}
