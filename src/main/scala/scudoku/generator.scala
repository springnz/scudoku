package scudoku

import scudoku.model._

import scala.annotation.tailrec
import scala.util.Random

object generator {

  @tailrec
  def generate(): Grid = {
    generateCandidate() match {
      case Some(grid) if solver.isMinimal(grid) =>
        grid
      case _ =>
        generate()
    }
  }

  def generateCandidate(): Option[Grid] = {
    val emptyGrid = Grid.emptyGrid
    for {
      randomSolution <- solver.solve(emptyGrid, randomise = true).headOption
      puzzle = unsolve(randomSolution)
    } yield puzzle
  }

  private def unsolve(solvedGrid: Grid): Grid = {
    val shuffledCells = Random.shuffle(allCells)
    shuffledCells.foldLeft(solvedGrid) {
      case (grid, cell) =>
        val updatedGrid = Grid(grid.cells - cell)
        val solutions = solver.solve(updatedGrid, 2)
        if (solutions.size == 1)
          updatedGrid
        else
          grid
    }
  }
}
