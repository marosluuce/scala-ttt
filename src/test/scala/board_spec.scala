import org.scalatest.FunSpec

class BoardSpec extends FunSpec {
  describe("move") {
    it("moves by setting a square to a value") {
      val board = new Board
      board.move(1, "x")

      expectResult("x        ") (board.getBoard)
    }
  }

  describe("undoMove") {
    it("undoes a move") {
      val board = new Board
      board.move(1, "x")
      board.undoMove(1)

      expectResult("         ") (board.getBoard)
    }
  }

  describe("getBoard") {
    it("gets the board") {
      val board = new Board
      expectResult("         ") (board.getBoard)

      for(i <- 1 to 9) { board.move(i, "x") }
      expectResult("xxxxxxxxx") (board.getBoard)
    }
  }

  describe("getAvailableSquares") {
    it("gets the available squares") {
      val board = new Board

      expectResult(Vector(1, 2, 3, 4, 5, 6, 7, 8, 9)) (board.getAvailableSquares)
    }
  }

  describe("validMove") {
    it("is valid of the square is available") {
      val board = new Board

      expectResult(true) (board.validMove(1))
    }

    it("is invalid if the square is not available") {
      val board = new Board
      board.move(1, "x")

      expectResult(false) (board.validMove(1))
    }
  }

  describe("full") {
    it("is false if all the squares are not filled") {
      val board = new Board
      expectResult(false) (board.full)

      board.move(1, "x")
      expectResult(false) (board.full)
    }

    it("is true if all the squares are filled") {
      val board = new Board
      for (i <- 1 to 9) { board.move(i, "x") }

      expectResult(true) (board.full)
    }
  }
}
