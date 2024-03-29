package scalattt.test

import org.scalatest.FunSpec
import org.scalatest.BeforeAndAfterEach

import scalattt.Board
import scalattt.Game
import scalattt.InvalidMoveException
import scalattt.Player

class GameSpec extends FunSpec with BeforeAndAfterEach {
  var board: Board = _
  var game: Game = _

  override def beforeEach {
    board = Board()
    game = Game(board)
  }

  describe("boardForPrint") {
    it("is a copy of the board ready for printing") {
      expectResult(board.formatted) (game.boardForPrint)
    }
  }

  describe("availableMoves") {
    it("is the board's available squares") {
      expectResult(board.availableMoves) (game.availableMoves)
    }
  }

  describe("gameover") {
    it("is the board's gameover") {
      expectResult(board.gameover) (game.gameover)
    }
  }

  describe("winner") {
    it("is the board's winner") {
      expectResult(board.winner) (game.winner)
    }
  }

  describe("reset") {
    it("resets the board") {
      for(x <- 1 to 9) (board.move(x, "x"))
      game.reset

      expectResult(Board.emptyBoard) (board.squares)
    }
  }

  describe("move") {
    it("makes a move if it is valid") {
      game.move(1, "x")
      expectResult("x") (board.squares(0))
    }

    it("throws an invalid move exeption if the move is invalid") {
      intercept[InvalidMoveException] (game.move(11111, "x"))
    }
  }

  describe("undoMove") {
    it("undoes a move") {
      game.move(1, "x")
      game.undoMove(1)

      assert(game.availableMoves.contains(1))
    }
  }

  describe("setPlayers") {
    it("sets the players to the passed in players") {
      val strategy = () => 1
      val p1 = new Player("x", strategy)
      val p2 = new Player("o", strategy)
      game.setPlayers(p1, p2)

      expectResult(Vector(p1, p2)) (game.players)
    }
  }

  describe("currentPlayer") {
    it("is player one if the number of moves made is even") {
      val strategy = () => 1
      val p1 = new Player("x", strategy)
      val p2 = new Player("o", strategy)
      game.setPlayers(p1, p2)

      expectResult(p1) (game.currentPlayer)
    }

    it("is player two if the number of moves made is even") {
      val strategy = () => 1
      val p1 = new Player("x", strategy)
      val p2 = new Player("o", strategy)
      game.setPlayers(p1, p2)
      game.move(1, "x")

      expectResult(p2) (game.currentPlayer)
    }
  }
}
