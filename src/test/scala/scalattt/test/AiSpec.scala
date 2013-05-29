package scalattt.test

import org.scalatest.FunSpec
import org.scalatest.BeforeAndAfterEach

import scalattt.Ai
import scalattt.Board
import scalattt.Game
import scalattt.Player

class AiSpec extends FunSpec with BeforeAndAfterEach {
  var board: Board = _
  var game: Game = _

  override def beforeEach {
    board = Board()
    game = new Game(board)
    game.setPlayers(Player("x", () => 1), Player("o", () => 1))
  }

  describe("hardAi") {
    it("takes a win") {
      board.squares = Vector("x", "x", " ", "o", "o", " ", " ", " ", " ")
      expectResult(3) (Ai.hard(game))
    }

    it("makes a block") {
      board.squares = Vector("x", " ", " ", "o", "o", " ", "x", " ", " ")
      expectResult(6) (Ai.hard(game))
    }

    it("forks the board") {
      board.squares = Vector("o", " ", " ", " ", "x", "o", " ", " ", "x")
      expectResult(7) (Ai.hard(game))
    }

    it("blocks a fork") {
      board.squares = Vector("x", " ", " ", " ", "o", " ", " ", " ", "x")
      expectResult(2) (Ai.hard(game))
    }

    it("plays in the center") {
      board.squares = Vector("x", " ", " ", " ", " ", " ", " ", " ", " ")
      expectResult(5) (Ai.hard(game))
    }
  }
}
