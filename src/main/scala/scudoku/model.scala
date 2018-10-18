package scudoku

object model {

  final case class Cell(row: Int, column: Int)
  final case class CellOptions(options: List[Int])

  val allValues: List[Int] = (1 to 9).toList
  val allRows: List[Int] = allValues
  val allColumns: List[Int] = allValues

  val allCells: List[Cell] = for {
    row <- allRows
    column <- allColumns
  } yield Cell(row, column)

  val rowUnits: List[List[Cell]] = for {
    row <- allRows
    unit = for {
      column <- allColumns
    } yield Cell(row, column)
  } yield unit

  val colUnits: List[List[Cell]] = for {
    column <- allColumns
    unit = for {
      row <- allRows
    } yield Cell(row, column)
  } yield unit

  val boxes = List(List(1, 2, 3), List(4, 5, 6), List(7, 8, 9))
  val boxUnits: List[List[Cell]] = for {
    boxRows <- boxes
    boxCols <- boxes
    unit = for {
      row <- boxRows
      column <- boxCols
    } yield Cell(row, column)
  } yield unit

  val allUnits: List[List[Cell]] = rowUnits ++ colUnits ++ boxUnits

  // List of units for this cell
  val cellUnits: Map[Cell, List[List[Cell]]] = (for {
    cell <- allCells
    units = allUnits.filter(u => u.contains(cell))
  } yield (cell, units)).toMap

  // Cells that share at least one unit with this cell
  val cellPeers: Map[Cell, List[Cell]] = (for {
    cell <- allCells
    units = cellUnits.getOrElse(cell, List())
    peers = units.flatMap(u => u.filterNot(_ == cell))
  } yield (cell, peers)).toMap


  final case class Grid(cells: Map[Cell, Int]) {

    override def toString: String = {
      (1 to 9).foldLeft("|-----------------------|\n") { (s, row) =>
        val columnStr = (1 to 9).foldLeft("|") { (s2, col) =>
          val cellValue = cells.get(Cell(row, col)).fold(".")(_.toString)
          if (col % 3 == 0)
            s"$s2 $cellValue |"
          else
            s"$s2 $cellValue"
        }
        if (row == 9)
          s"$s$columnStr\n|-----------------------|\n"
        else if (row % 3 == 0) {
          s"$s$columnStr\n|-------|-------|-------|\n"
        } else {
          s"$s$columnStr\n"
        }
      }
    }
  }

  object Grid {
    val emptyGrid: Grid = Grid(Map())
  }

}
