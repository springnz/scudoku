package scudoku

import java.io.{File, PrintWriter}

import scala.io.{Source, StdIn}
import scala.util.{Success, Try}

object main extends App {

  val conf = cli.Conf(args)
  conf.subcommand match {
    case Some(command) if command == conf.solve =>
      conf.solve.inputFile.toOption match {
        case Some(f) =>
          solve(f, conf.solve.maxSolutions.toOption)
        case None =>
          println("No input specified")
      }
    case Some(command) if command == conf.generate =>
      generate(conf.generate.outputFile.toOption)
    case _ =>
      println("Unknown subcommand")
  }

  private def generate(outputFile: Option[String]): Unit = {
    val grid = generator.generate()
    println(grid.toString)
    outputFile.foreach { f =>
      val file = new File(f)
      Try(new PrintWriter(file)) match {
        case Success(writer) =>
          writer.write(grid.toString)
          writer.close()
          println(s"Grid written to ${file.getAbsolutePath}")
        case _ => println(s"Unable to open file: $f")
      }
    }
  }

  private def solve(inputFile: String, maxSolutions: Option[Int]): Unit = {
    Try(Source.fromFile(inputFile)) match {
      case Success(file) =>
        parser.parseGrid(file.mkString) match {
          case Some(grid) =>
            println("Grid to solve:")
            println(grid.toString)

            println("Hit <enter> to start...")
            val in = StdIn.readLine()
            println("Solving...")
            val time = System.currentTimeMillis()
            val result = solver.solve(grid, maxSolutions.getOrElse(1))
            println(s"Took ${System.currentTimeMillis() - time}ms")
            println(s"Found ${result.size} solution(s)")
            result.foreach(r => println(r.toString))
          case _ =>
            println("Invalid grid")
        }
      case _ => println(s"Unable to read file: $inputFile")
    }
  }
}
