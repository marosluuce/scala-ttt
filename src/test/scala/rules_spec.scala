import org.scalatest.FunSpec

class RulesSpec extends FunSpec {

  describe("winnerIs") {
    it("is true for 'xxx      '") {
      val board = new MockBoard
      val rules = new Rules(board)
      board.currentBoard = Vector("x", "x", "x", " ", " ", " ", " ", " ", " ")

      expectResult(true) (rules.winnerIs("x"))
    }

    it("is true for '   xxx   '") {
      val board = new MockBoard
      val rules = new Rules(board)
      board.currentBoard = Vector(" ", " ", " ", "x", "x", "x", " ", " ", " ")

      expectResult(true) (rules.winnerIs("x"))
    }

    it("is true for '      xxx'") {
      val board = new MockBoard
      val rules = new Rules(board)
      board.currentBoard = Vector(" ", " ", " ", " ", " ", " ", "x", "x", "x")

      expectResult(true) (rules.winnerIs("x"))
    }

    it("is true for 'x  x  x  '") {
      val board = new MockBoard
      val rules = new Rules(board)
      board.currentBoard = Vector("x", " ", " ", "x", " ", " ", "x", " ", " ")

      expectResult(true) (rules.winnerIs("x"))
    }

    it("is true for ' x  x  x '") {
      val board = new MockBoard
      val rules = new Rules(board)
      board.currentBoard = Vector(" ", "x", " ", " ", "x", " ", " ", "x", " ")

      expectResult(true) (rules.winnerIs("x"))
    }

    it("is true for '  x  x  x'") {
      val board = new MockBoard
      val rules = new Rules(board)
      board.currentBoard = Vector(" ", " ", "x", " ", " ", "x", " ", " ", "x")

      expectResult(true) (rules.winnerIs("x"))
    }

    it("is true for 'x   x   x'") {
      val board = new MockBoard
      val rules = new Rules(board)
      board.currentBoard = Vector("x", " ", " "," ", "x", " ",  " ", " ", "x")

      expectResult(true) (rules.winnerIs("x"))
    }

    it("is true for '  x x x  '") {
      val board = new MockBoard
      val rules = new Rules(board)
      board.currentBoard = Vector(" ", " ", "x", " ", "x", " ", "x", " ", " ")

      expectResult(true) (rules.winnerIs("x"))
    }

    it("is false for 'xxo'") {
      val board = new MockBoard
      val rules = new Rules(board)
      board.currentBoard = Vector("x", "x", "o", " ", " ", " ", " ", " ", " ")

      expectResult(false) (rules.winnerIs("o"))
    }
  }

  describe("gameover") {
    it("is true if the board is full") {
      val board = new MockBoard
      val rules = new Rules(board)
      board.isFull = true

      expectResult(true) (rules.gameover)
    }

    it("is true if there is a winner") {
      val board = new MockBoard
      val rules = new Rules(board)
      board.currentBoard = Vector("x", "o", "x", "x", "o", "x", "x", "o", "x")

      expectResult(true) (rules.gameover)
    }

    it("is false if there is no winner and the board is not full") {
      val board = new MockBoard
      val rules = new Rules(board)
      board.currentBoard = Vector("x", "x", "o", " ", " ", " ", " ", " ", " ")

      expectResult(false) (rules.gameover)
    }
  }

  describe("draw") {
    it("is false if the board is not full") {
      var rules = new Rules(new Board)

      expectResult(false) (rules.draw)
    }

    it("is false if there is a winner") {
      var board = new MockBoard
      var rules = new Rules(board)
      board.currentBoard = Vector("x", "x", "x", " ", " ", " ", " ", " ", " ")

      expectResult(false) (rules.draw)
    }

    it("is true if the board is full and there is no winner") {
      var board = new MockBoard
      var rules = new Rules(board)
      board.currentBoard = Vector("x", "o", "x", "x", "o", "x", "o", "x", "o")
      board.isFull = true

      expectResult(true) (rules.draw)
    }
  }
}

class MockBoard extends Board {
  var currentBoard = Vector[String]()
  var isFull = false

  override def getBoard = currentBoard
  override def full = isFull
}
