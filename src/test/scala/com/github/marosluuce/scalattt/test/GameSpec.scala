package com.github.marosluuce.scalattt.test

import org.scalatest.FunSpec
import org.scalatest.BeforeAndAfterEach

import com.github.marosluuce.scalattt.Board
import com.github.marosluuce.scalattt.Game

class GameSpec extends FunSpec with BeforeAndAfterEach {
  var board: MockBoard = _
  var game: Game = _

  override def beforeEach {
    board = new MockBoard
    game = new Game(board)
  }

  describe("formattedBoard") {
    it("is a formatted copy of the board") {
      board.formattedBoard = Vector("a", "b", "c")
      expectResult(board.formatted) (game.formattedBoard)
    }
  }
}

class MockBoard extends Board {
  var formattedBoard = Vector[String]()

  override def formatted = formattedBoard
}
