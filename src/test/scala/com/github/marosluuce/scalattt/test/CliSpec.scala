package com.github.marosluuce.scalattt.test

import org.scalatest.FunSpec

import com.github.marosluuce.scalattt.Cli
import com.github.marosluuce.scalattt.Game
import com.github.marosluuce.scalattt.Io

import com.github.marosluuce.scalattt.test.mock.MockIo

class CliSpec extends FunSpec {
  it("has a game") {
    val cli = new Cli(new Game, null)
    expectResult(classOf[Game]) (cli.game.getClass)
  }

  it("has io") {
    val cli = new Cli(new Game, new Io)
    expectResult(classOf[Io]) (cli.io.getClass)
  }

  describe("promptMove") {
    it("displays a move prompt") {
      val io = new MockIo
      val cli = new Cli(new Game, io)
      io.input = List("1")
      cli.promptMove

      assert(io.output.contains(Cli.movePrompt))
    }

    it("returns the user's input") {
      val io = new MockIo
      val cli = new Cli(new Game, io)
      io.input = List("1\n")

      expectResult(1) (cli.promptMove)
    }

    it("prints an error and tries again for invalid input") {
      val io = new MockIo
      val cli = new Cli(new Game, io)
      io.input = List("a\n", "1\n")

      expectResult(1) (cli.promptMove)
      assert(io.output.contains(Cli.invalidInput + "\n"), "Failed to print error message")
    }
  }

  //describe("outputBoard") {
    //val io = new MockIO
    //val cli = new Cli(new Game, io)
    //cli.printBoard
  //}
}
