package com.github.marosluuce.scalattt.test

import org.scalatest.FunSpec
import org.scalatest.BeforeAndAfterEach

import com.github.marosluuce.scalattt.Cli
import com.github.marosluuce.scalattt.Game
import com.github.marosluuce.scalattt.Io
import com.github.marosluuce.scalattt.Player

import com.github.marosluuce.scalattt.test.mock.MockIo

class CliSpec extends FunSpec with BeforeAndAfterEach {
  var io: MockIo = _
  var game: Game = _
  var cli: Cli = _

  override def beforeEach() {
    io = new MockIo
    game = Game()
    cli = new Cli(game, io)
  }

  describe("aiPlayer") {
    it("creates a player with a symbol and an ai strategy") {
      val player = cli.aiPlayer("x")

      expectResult("x") (player.symbol)
      expectResult(cli.aiStrategy) (player.strategy)
    }
  }

  describe("humanPlayer") {
    it("creates a player with a symbol and a human strategy") {
      val player = cli.humanPlayer("x")

      expectResult("x") (player.symbol)
      expectResult(cli.humanStrategy) (player.strategy)
    }
  }

  describe("run") {
    it("is game over when the game is finished") {
      val strategy = () => game.availableMoves.head
      game.setPlayers(Player("x", strategy), Player("o", strategy))
      cli.run

      assert(game.gameover, "The game is not over")
    }
  }

  describe("takeTurn") {
    it("makes a move and draws the board") {
      io.input = List("1\n")
      game.setPlayers(Player("x", cli.promptMove _), Player("o", cli.promptMove _))
      cli.takeTurn

      expectResult("x") (game.board.squares(0))
      assert(io.output.contains(Cli.boardRow.format("x", "2", "3")+"\n"))
    }
  }

  describe("promptMove") {
    it("displays a move prompt") {
      io.input = List("1")
      cli.promptMove

      assert(io.output.contains(Cli.movePrompt))
    }

    it("returns the user's input") {
      io.input = List("1\n")

      expectResult(1) (cli.promptMove)
    }

    it("prints an error and tries again for invalid input") {
      io.input = List("a\n", "1\n")

      expectResult(1) (cli.promptMove)
      assert(io.output.contains(Cli.invalidInput + "\n"), "Failed to print error message")
    }
  }

  describe("printBoard") {
    it("prints a formatted board") {
      cli.printBoard

      val printedBoard = List(" 1 | 2 | 3 \n",
                              "---|---|---\n",
                              " 4 | 5 | 6 \n",
                              "---|---|---\n",
                              " 7 | 8 | 9 \n")

      expectResult(printedBoard) (io.output)
    }
  }
}
