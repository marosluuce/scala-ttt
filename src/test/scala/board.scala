import org.scalatest.FunSpec

class BoardSpec extends FunSpec {
  describe("move") {
    it("moves by setting a square to a value") {
      val board = new Board
      board.move(1, "x")

      expectResult("x        ") (board.getBoard)
    }
  }

  describe("getBoard") {
    it("gets the board") {
      val board = new Board

      expectResult("         ") (board.getBoard)
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
}
