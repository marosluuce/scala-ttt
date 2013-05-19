import org.scalatest.FunSpec

class RulesSpec extends FunSpec {

  describe("winnerIs") {
    it("is true for 'xxx      '") {
      val board = new MockBoard
      val rules = new Rules(board)
      board.currentBoard = "xxx      "

      expectResult(true) (rules.winnerIs("x"))
    }

    it("is true for '   xxx   '") {
      val board = new MockBoard
      val rules = new Rules(board)
      board.currentBoard = "   xxx   "

      expectResult(true) (rules.winnerIs("x"))
    }

    it("is true for '      xxx'") {
      val board = new MockBoard
      val rules = new Rules(board)
      board.currentBoard = "      xxx"

      expectResult(true) (rules.winnerIs("x"))
    }

    it("is true for 'x  x  x  '") {
      val board = new MockBoard
      val rules = new Rules(board)
      board.currentBoard = "x  x  x  "

      expectResult(true) (rules.winnerIs("x"))
    }

    it("is true for ' x  x  x '") {
      val board = new MockBoard
      val rules = new Rules(board)
      board.currentBoard = " x  x  x "

      expectResult(true) (rules.winnerIs("x"))
    }

    it("is true for '  x  x  x'") {
      val board = new MockBoard
      val rules = new Rules(board)
      board.currentBoard = "  x  x  x"

      expectResult(true) (rules.winnerIs("x"))
    }

    it("is true for 'x   x   x'") {
      val board = new MockBoard
      val rules = new Rules(board)
      board.currentBoard = "x   x   x"

      expectResult(true) (rules.winnerIs("x"))
    }

    it("is true for '  x x x  '") {
      val board = new MockBoard
      val rules = new Rules(board)
      board.currentBoard = "  x x x  "

      expectResult(true) (rules.winnerIs("x"))
    }

    it("is false for 'xxo'") {
      val board = new MockBoard
      val rules = new Rules(board)
      board.currentBoard = "xxo      "

      expectResult(false) (rules.winnerIs("o"))
    }
  }

  describe("gameover") {
    it("is true if the board is full") {
      val board = new Board
      for (i <- 1 to 9) { board.move(i, "x") }
      val rules = new Rules(board)

      expectResult(true) (rules.gameover)
    }

    it("is true if there is a winner") {
      val board = new Board
      for (i <- 1 to 3) { board.move(i, "x") }
      val rules = new Rules(board)

      expectResult(true) (rules.gameover)
    }
  }

  describe("draw") {
    it("is false if the board is not full") {
      var rules = new Rules(new Board)

      expectResult(false) (rules.draw)
    }

    it("is false if there is a winner") {
      var board = new MockBoard
      board.currentBoard = "xxx      "
      var rules = new Rules(board)

      expectResult(false) (rules.draw)
    }

    it("is true if the board is full and there is no winner") {
      var board = new MockBoard
      board.currentBoard = "xoxxoxoxo"
      board.isFull = true
      var rules = new Rules(board)

      expectResult(true) (rules.draw)
    }
  }
}

class MockBoard extends Board {
  var currentBoard = ""
  var isFull = false

  override def getBoard = currentBoard
  override def full = isFull
}
