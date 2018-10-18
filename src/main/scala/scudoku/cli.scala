package scudoku

import org.rogach.scallop._

object cli {
  final case class Conf(arguments: Seq[String]) extends ScallopConf(arguments) {
    val solve = new Subcommand("solve") {
      val inputFile = opt[String]("input-file", required = true)
      val maxSolutions = opt[Int]("max", descr = "Max solutions to find", default = Option(1))
    }

    val generate = new Subcommand("generate", "gen") {
      val outputFile = opt[String]("output-file")
    }

    addSubcommand(solve)
    addSubcommand(generate)
    verify()
  }
}
