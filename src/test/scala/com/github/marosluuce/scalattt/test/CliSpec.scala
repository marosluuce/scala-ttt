package com.github.marosluuce.scalattt.test

import org.scalatest.FunSpec
import org.scalatest.BeforeAndAfterEach

import com.github.marosluuce.scalattt.Cli
import com.github.marosluuce.scalattt.Game
import com.github.marosluuce.scalattt.Io

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
