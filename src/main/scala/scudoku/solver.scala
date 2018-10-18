package scudoku

import scudoku.model.Grid

import scala.util.Random

object solver {

  def solve(grid: Grid, maxSolutions: Int = 1, randomise: Boolean = false): List[Grid] =
    OptionsGrid.fromGrid(grid) match {
      case Some(optionsGrid) =>
        findSolutions(optionsGrid, maxSolutions, randomise).map(_.toGrid)
      case _ =>
        List.empty[Grid]
    }

  def isMinimal(grid: Grid): Boolean = {
    grid.cells.foldLeft(true) {
      case (minimal, (cell, _)) =>
        if (!minimal) {
          minimal
        } else {
          OptionsGrid.fromGrid(Grid(grid.cells - cell)).fold(false) { updated =>
            findSolutions(updated, maxSolutions = 2, randomise = false).size != 1
          }
        }
    }
  }

  private def findSolutions(grid: OptionsGrid, maxSolutions: Int, randomise: Boolean): List[OptionsGrid] = {
    if (maxSolutions == 0 || !grid.isValid)
      List.empty
    else if (grid.isSolved)
      List(grid)
    else {
      grid.emptyCellWithFewestOptions.fold(List.empty[OptionsGrid]) {
        case (cell, cellValue) =>
          val options = if (randomise)
            Random.shuffle(cellValue.options)
          else
            cellValue.options

          options.foldLeft(List.empty[OptionsGrid]) { (found, x) =>
            if (found.size >= maxSolutions) {
              found
            } else {
              val nextGrid = OptionsGrid.assign(grid, cell, x)
              val newSolutions = nextGrid.toList.flatMap(g => findSolutions(g, maxSolutions - found.size, randomise))
              found ++ newSolutions
            }
          }
      }
    }
  }
}
