import org.scalatest.{MustMatchers, WordSpec}
import scudoku.model.Grid
import scudoku.{parser, solver}

class SolverTest extends WordSpec with MustMatchers {
  "solve" when {
    "empty grid" must {
      "find multiple solutions" in {
        solver.solve(Grid(Map()), 5) must have size 5
      }
    }
    "invalid grid" must {
      "find a solution" in {
        val grid = parser.parseGrid(
          """
            |123 123 ...
            |456 456 ...
            |789 789 ...
            |
            |... ... ...
            |... ... ...
            |... ... ...
            |
            |... ... ...
            |... ... ...
            |... ... ...
          """.stripMargin).get
        solver.solve(grid, 1) mustBe 'empty
      }
    }
    "valid grid with unique solution" must {
      "find exactly one solution" in {
        val grid = parser.parseGrid(
          """
            |... ..8 793
            |... 1.9 ...
            |... .26 ...
            |
            |.86 ... ..5
            |735 ... ...
            |1.. .4. 6..
            |
            |... ... 3..
            |34. ... 17.
            |..7 8.. ..6
            |
          """.stripMargin).get
        solver.solve(grid, 2) must have size 1
      }
    }
  }
}
