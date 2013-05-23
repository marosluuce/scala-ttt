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

  describe("playerSelectMenu") {
    it("is the player's options and a prompt") {
      val menu = Cli.menuAndPrompt.format(Cli.playerMenuOptions, Cli.menuPrompt)
      expectResult(menu) (Cli.playerSelectMenu)
    }
  }

  describe("playAganMenu") {
    it("is the options and a prompt") {
      val menu = Cli.menuAndPrompt.format(Cli.playAgainOptions, Cli.menuPrompt)
      expectResult(menu) (Cli.playAgainMenu)
    }
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
    it("prints the board before making a move") {
      game.setPlayers(Player("x", () => 1), Player("o", () => 1))
      val initialBoard = cli.formattedBoard
      cli.takeTurn

      assert(io.output.contains(initialBoard))
    }

    it("has the current player make a move") {
      game.setPlayers(Player("x", () => 1), Player("o", () => 1))
      cli.takeTurn

      expectResult("x") (game.board.squares(0))
    }

    it("prints an error and takes another turn if it catches an invalid move exception") {
      game.setPlayers(cli.humanPlayer("x"), Player("o", () => 1))
      io.input = List("11111", "1")
      cli.takeTurn

      assert(io.output.contains(Cli.invalidMove+"\n"))
    }
  }

  describe("promptPlayerChoice") {
    it("displays a player choice prompt") {
      io.input = List("1")
      cli.promptPlayerChoice

      expectResult(Cli.playerSelectMenu) (io.output.head)
    }
    it("returns the user's input") {
      io.input = List("1")
      expectResult(1) (cli.promptPlayerChoice)
    }

    it("prints an error and tries again for invalid input") {
      io.input = List("a\n", "1\n")

      expectResult(1) (cli.promptPlayerChoice)
      assert(io.output.contains(Cli.invalidInput + "\n"), "Failed to print error message")
    }
  }

  describe("promptMove") {
    it("displays a move prompt") {
      io.input = List("1")
      cli.promptMove

      expectResult(Cli.movePrompt) (io.output.head)
    }

    it("returns the user's input") {
      io.input = List("1")
      expectResult(1) (cli.promptMove)
    }

    it("prints an error and tries again for invalid input") {
      io.input = List("a\n", "1\n")

      expectResult(1) (cli.promptMove)
      assert(io.output.contains(Cli.invalidInput + "\n"), "Failed to print error message")
    }
  }

  describe("promptPlayAgain") {
    it("displays a prompt to play again") {
      io.input = List("1")
      cli.promptPlayAgain

      expectResult(Cli.playAgainMenu) (io.output.head)
    }

    it("returns the user's input") {
      io.input = List("1")
      expectResult(1) (cli.promptPlayAgain)
    }

    it("prints an error and tries again for invalid input") {
      io.input = List("a\n", "1\n")

      expectResult(1) (cli.promptPlayAgain)
      assert(io.output.contains(Cli.invalidInput + "\n"), "Failed to print error message")
    }
  }

  describe("formattedBoard") {
    it("formats the board for printing") {
      val formattedBoard = " 1 | 2 | 3 \n---|---|---\n 4 | 5 | 6 \n---|---|---\n 7 | 8 | 9 \n"
      expectResult(formattedBoard) (cli.formattedBoard)
    }
  }

  describe("printBoard") {
    it("prints a formatted board") {
      cli.printBoard
      assert(io.output.contains(cli.formattedBoard))
    }
  }

  describe("printOutcome") {
    it("prints the board first") {
      cli.printOutcome
      expectResult(cli.formattedBoard) (io.output.head)
    }

    it("prints the win message if there is a winner") {
      game.board.squares = Vector.fill(9)("x")
      cli.printOutcome
      assert(io.output.contains(Cli.winMessage.format("x")+"\n"), "Winner was not printed")
    }

    it("prints the draw message otherwise") {
      cli.printOutcome
      assert(io.output.contains(Cli.drawMessage+"\n"), "Draw was not printed")
    }
  }
}
