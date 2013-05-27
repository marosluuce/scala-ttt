package com.github.marosluuce.scalattt.test

import org.scalatest.FunSpec
import org.scalatest.BeforeAndAfterEach

import com.github.marosluuce.scalattt.Board
import com.github.marosluuce.scalattt.Cli
import com.github.marosluuce.scalattt.Game
import com.github.marosluuce.scalattt.InvalidChoiceException
import com.github.marosluuce.scalattt.Io
import com.github.marosluuce.scalattt.Menu
import com.github.marosluuce.scalattt.Player

import com.github.marosluuce.scalattt.test.mock.MockIo
import com.github.marosluuce.scalattt.test.mock.MockMenu

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
      val menu = Cli.menuAndPrompt.format(Cli.playerSelectTitle, Cli.playerSelectOptions, Cli.menuPrompt)
      expectResult(menu) (Cli.playerSelectMenu)
    }
  }

  describe("playAganMenu") {
    it("is the options and a prompt") {
      val menu = Cli.menuAndPrompt.format(Cli.playAgainTitle, Cli.playAgainOptions, Cli.menuPrompt)
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
    it("prints the greeting first") {
      val strategy = () => game.availableMoves.head
      game.setPlayers(Player("x", strategy), Player("o", strategy))
      cli.runPlayerSelectMenu = new MockMenu(() => Unit, io)
      cli.runPlayAgainMenu = new MockMenu(() => Unit, io)
      cli.run

      expectResult(Cli.greeting+"\n") (io.output.head)
    }

    it("is game over when the game is finished") {
      val strategy = () => game.availableMoves.head
      game.setPlayers(Player("x", strategy), Player("o", strategy))
      cli.runPlayerSelectMenu = new MockMenu(() => Unit, io)
      cli.runPlayAgainMenu = new MockMenu(() => Unit, io)
      cli.run

      assert(game.gameover, "The game is not over")
    }

    it("calls the player select menu") {
      val strategy = () => game.availableMoves.head
      game.setPlayers(Player("x", strategy), Player("o", strategy))
      var i = 0
      cli.runPlayerSelectMenu = new MockMenu(() => i += 1, io)
      cli.runPlayAgainMenu = new MockMenu(() => Unit, io)
      cli.run

      expectResult(1) (i)
    }

    it("prints the outcome of a game") {
      game.board.squares = Vector("x", "x", "x", " ", " ", " ", " ", " ", " ")
      cli.runPlayerSelectMenu = new MockMenu(() => Unit, io)
      cli.runPlayAgainMenu = new MockMenu(() => Unit, io)
      cli.run

      assert(io.didOutput(cli.formattedBoard))
      assert(io.didOutput(Cli.winMessage.format("x")))
    }

    it("calls the play again menu") {
      val strategy = () => game.availableMoves.head
      game.setPlayers(Player("x", strategy), Player("o", strategy))
      var i = 0
      cli.runPlayerSelectMenu = new MockMenu(() => i += 1, io)
      cli.runPlayAgainMenu = new MockMenu(() => Unit, io)
      cli.run

      expectResult(1) (i)
    }

    it("plays again if the player selected play again in the menu") {
      val strategy = () => game.availableMoves.head
      game.setPlayers(Player("x", strategy), Player("o", strategy))
      var i = 0
      val again = () => { i += 1; if (i <= 1) { game.reset } }
      cli.runPlayerSelectMenu = new MockMenu(() => Unit, io)
      cli.runPlayAgainMenu = new MockMenu(again, io)
      cli.run

      expectResult(2) (i)
    }
  }

  describe("takeTurn") {
    it("prints the board first") {
      game.setPlayers(Player("x", () => 1), Player("o", () => 1))
      val initialBoard = cli.formattedBoard
      cli.takeTurn

      expectResult(initialBoard) (io.output.filter(_!="\n").head)
    }

    it("prints whose turn it is") {
      game.setPlayers(Player("x", () => 1), Player("o", () => 1))
      cli.takeTurn

      assert(io.didOutput(Cli.playersTurn.format("x")))
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

      assert(io.didOutput(Cli.invalidMove))
      expectResult("x") (game.board.squares(0))
    }
  }

  describe("runPlayerSelectMenu") {
    it("displays the menu") {
      io.input = List("1")
      cli.runPlayerSelectMenu()
      expectResult(Cli.playerSelectMenu) (io.output.filter(_!="\n").head)
    }

    it("creates players if a valid choice is entered") {
      io.input = List("1")
      cli.runPlayerSelectMenu()

      expectResult(cli.humanStrategy) (game.players.head.strategy)
      expectResult(cli.humanStrategy) (game.players.last.strategy)
    }

    it("prompts the user again if an invalid choice is made") {
      io.input = List("100", "2")
      cli.runPlayerSelectMenu()

      expectResult(cli.humanStrategy) (game.players.head.strategy)
      expectResult(cli.aiStrategy) (game.players.last.strategy)
    }
  }

  describe("runPlayAgainMenu") {
    it("displays the menu") {
      io.input = List("1")
      cli.runPlayAgainMenu()
      expectResult(Cli.playAgainMenu) (io.output.filter(_!="\n").head)
    }

    it("resets the game when the user wants to play again") {
      io.input = List("1")
      cli.runPlayAgainMenu()

      expectResult(Board.emptyBoard) (game.board.squares)
    }

    it("prints an error message and prompts the user again if an invalid choice is made") {
      io.input = List("100", "2")
      cli.runPlayAgainMenu()

      assert(io.didOutput(Cli.invalidChoice))
      expectResult(Board.emptyBoard) (game.board.squares)
    }
  }

  describe("playAgain") {
    it("resets the game if 1") {
      for(x <- 1 to 9) (game.move(x, "x"))
      cli.playAgain(1)

      expectResult(Board.emptyBoard) (game.board.squares)
    }

    it("does nothing if 2") {
      expectResult(()) (cli.playAgain(2))
    }

    it("throws an exception for any other input") {
      intercept[InvalidChoiceException](cli.playAgain(1001))
    }
  }

  describe("createPlayers") {
    it("creates two human players when given 1") {
      cli.createPlayers(1)
      expectResult(cli.humanStrategy) (game.players.head.strategy)
      expectResult(cli.humanStrategy) (game.players.last.strategy)
    }

    it("creates a human and ai player when given 2") {
      cli.createPlayers(2)
      expectResult(cli.humanStrategy) (game.players.head.strategy)
      expectResult(cli.aiStrategy) (game.players.last.strategy)
    }

    it("creates an ai and human player when given 3") {
      cli.createPlayers(3)
      expectResult(cli.aiStrategy) (game.players.head.strategy)
      expectResult(cli.humanStrategy) (game.players.last.strategy)
    }

    it("creates two ai players when given 4") {
      cli.createPlayers(4)
      expectResult(cli.aiStrategy) (game.players.head.strategy)
      expectResult(cli.aiStrategy) (game.players.last.strategy)
    }

    it("throws an exception when given an invalid choice") {
      intercept[InvalidChoiceException] (cli.createPlayers(1000))
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
      assert(io.didOutput(Cli.invalidInput), "Failed to print error message")
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
      assert(io.didOutput(cli.formattedBoard))
    }
  }

  describe("printOutcome") {
    it("prints the board first") {
      cli.printOutcome
      expectResult(cli.formattedBoard) (io.output.filter(_!="\n").head)
    }

    it("prints the win message if there is a winner") {
      game.board.squares = Vector.fill(9)("x")
      cli.printOutcome
      assert(io.didOutput(Cli.winMessage.format("x")), "Winner was not printed")
    }

    it("prints the draw message otherwise") {
      cli.printOutcome
      assert(io.didOutput(Cli.drawMessage), "Draw was not printed")
    }
  }
}
