package scudoku

import scudoku.model._

final case class OptionsGrid(cells: Map[Cell, CellOptions]) {

  def isValid: Boolean =
    cells.forall { case (_, value) => value.options.nonEmpty }

  def isSolved: Boolean =
    cells.forall { case (_, value) => value.options.size == 1 }

  def emptyCellWithFewestOptions: Option[(Cell, CellOptions)] = {
    cells.foldLeft(Option.empty[(Cell, CellOptions)]) {
      case (prev, (cell, value)) => value.options match {
        case Nil => None
        case head :: Nil => prev
        case options =>
          prev.map(_._2.options).fold(Option((cell, value))) { prevOptions =>
            if (options.size < prevOptions.size) Some((cell, value))
            else prev
          }
      }
    }
  }

  def toGrid: Grid = {
    Grid(cells.collect {
      case (cell, CellOptions(fixedValue :: Nil)) => (cell, fixedValue)
    })
  }
}

object OptionsGrid {

  def apply(): OptionsGrid = OptionsGrid(allCells.map(c => (c, CellOptions(allValues))).toMap)

  def fromGrid(grid: Grid): Option[OptionsGrid] = {
    val empty = OptionsGrid()
    grid.cells.foldLeft(Option(empty)) { (current, entry) =>
      current.flatMap { g =>
        assign(g, entry._1, entry._2)
      }
    }
  }

  def emptyGrid: OptionsGrid =
    OptionsGrid(allCells.map(cell => (cell, CellOptions(allValues))).toMap)

  def assign(grid: OptionsGrid, cell: Cell, value: Int): Option[OptionsGrid] = {
    grid.cells.get(cell).flatMap { currentValue =>
      val remaining = currentValue.options.filterNot(_ == value)
      remaining.foldLeft(Option(grid)) { (maybeGrid, v) =>
        maybeGrid.flatMap(g => eliminate(g, cell, v))
      }
    }
  }

  def eliminate(grid: OptionsGrid, cell: Cell, value: Int): Option[OptionsGrid] = {
    val options = grid.cells.get(cell).map(_.options).getOrElse(List())
    if (!options.contains(value)) {
      Some(OptionsGrid(grid.cells)) // already eliminated
    } else {
      val remaining = options.filterNot(_ == value)
      val updatedGrid = OptionsGrid(grid.cells.updated(cell, CellOptions(remaining)))
      val updated = remaining match {
        case Nil => None // trying to eliminate last value
        case cellValue :: Nil => // Last option for this cell - eliminate the value from peers
          val peers = cellPeers.getOrElse(cell, List())
          peers.foldLeft(Option(updatedGrid)) { (maybeGrid, peer) =>
            maybeGrid.flatMap(g => eliminate(g, peer, cellValue))
          }
        case _ => Some(updatedGrid)
      }
      updated.flatMap { g =>
        val units = cellUnits.getOrElse(cell, List())
        val placesForValue = (for {
          unit <- units
          places <- unit.filter(c => g.cells.get(c).map(_.options).getOrElse(List()).contains(value))
        } yield places).filterNot(c => c == cell)
        placesForValue match {
          case Nil => None
          case place :: Nil => assign(g, place, value)
          case _ => Some(g)
        }
      }
    }
  }
}
