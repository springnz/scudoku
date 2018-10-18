package scudoku

import scudoku.model.{Cell, Grid}

object parser {

  // Parse a sudoku grid from text.  Period chars ('.') are interpreted as empty cells. Any other non-digit chars are ignored.
  def parseGrid(input: String): Option[Grid] = {
    val values = input.replaceAll("\\.", "0").replaceAll("\\D", "").toList.map(c => Integer.parseInt(c.toString))
    if (values.length != 81)
      None
    else {
      val gridValues = values.zipWithIndex.filter(_._1 != 0).map { cell =>
        val row = cell._2 / 9 + 1
        val col = cell._2 % 9 + 1
        (Cell(row, col), cell._1)
      }
      Option(Grid(gridValues.toMap))
    }
  }
}
