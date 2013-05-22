package com.github.marosluuce.scalattt.test

import org.scalatest.FunSpec
import org.scalatest.BeforeAndAfterEach

import com.github.marosluuce.scalattt.Board

class BoardSpec extends FunSpec with BeforeAndAfterEach {
  var board: Board = _

  override def beforeEach() {
    board = Board()
  }

  describe("move") {
    it("moves by setting a square to a value") {
      expectResult(" ") (board.squares(0))

      board.move(1, "x")
      expectResult("x") (board.squares(0))
    }
  }

  describe("undoMove") {
    it("undoes a move") {
      board.move(1, "x")
      board.undoMove(1)

      expectResult(" ") (board.squares(0))
    }
  }

  describe("availableMoves") {
    it("gets the available moves") {
      expectResult(Vector(1, 2, 3, 4, 5, 6, 7, 8, 9)) (board.availableMoves)

      board.move(1, "x")
      expectResult(Vector(2, 3, 4, 5, 6, 7, 8, 9)) (board.availableMoves)
    }
  }

  describe("validMove") {
    it("is valid of the square is available") {
      expectResult(true) (board.validMove(1))
    }

    it("is invalid if the square is not available") {
      board.move(1, "x")
      expectResult(false) (board.validMove(1))
    }
  }

  describe("full") {
    it("is false if all the squares are not filled") {
      expectResult(false) (board.full)

      board.move(1, "x")
      expectResult(false) (board.full)
    }

    it("is true if all the squares are filled") {
      board.squares = Vector.fill(9)("x")
      expectResult(true) (board.full)
    }
  }

  describe("winner") {
    it("is true for 'xxx      '") {
      val winningCombos = Vector(Vector("x", "x", "x", " ", " ", " ", " ", " ", " "),
                                 Vector(" ", " ", " ", "x", "x", "x", " ", " ", " "),
                                 Vector(" ", " ", " ", " ", " ", " ", "x", "x", "x"),
                                 Vector("x", " ", " ", "x", " ", " ", "x", " ", " "),
                                 Vector(" ", "x", " ", " ", "x", " ", " ", "x", " "),
                                 Vector(" ", " ", "x", " ", " ", "x", " ", " ", "x"),
                                 Vector("x", " ", " "," ", "x", " ",  " ", " ", "x"),
                                 Vector(" ", " ", "x", " ", "x", " ", "x", " ", " "))

      for(combo <- winningCombos) {
        board.squares = combo
        expectResult(Some("x")) (board.winner)
      }
    }

    it("is false for 'xxo      '") {
      board.squares = Vector("x", "x", "o", " ", " ", " ", " ", " ", " ")
      expectResult(None) (board.winner)
    }
  }

  describe("gameover") {
    it("is true if the board is full") {
      board.squares = Vector("x", "o", "x", "x", "o", "x", "x", "o", "x")
      expectResult(true) (board.gameover)
    }

    it("is true if there is a winner") {
      board.squares = Vector("x", "o", "x", "x", "o", "x", "x", "o", "x")
      expectResult(true) (board.gameover)
    }

    it("is false if there is no winner and the board is not full") {
      board.squares = Vector("x", "x", "o", " ", " ", " ", " ", " ", " ")
      expectResult(false) (board.gameover)
    }
  }

  describe("draw") {
    it("is false if the board is not full") {
      board.squares = Vector.fill(9)(" ")
      expectResult(false) (board.draw)
    }

    it("is false if there is a winner") {
      board.squares = Vector("x", "x", "x", " ", " ", " ", " ", " ", " ")
      expectResult(false) (board.draw)
    }

    it("is true if the board is full and there is no winner") {
      board.squares = Vector("x", "o", "x", "x", "o", "x", "o", "x", "o")
      expectResult(true) (board.draw)
    }
  }

  describe("formatted") {
    it("leaves the symbols as symbols") {
      board.move(1, "x")
      expectResult("x") (board.formatted(0))
    }

    it("converts the empty squares to their number") {
      board.squares = Vector.fill(9)(" ")
      expectResult(Vector("1", "2", "3", "4", "5", "6", "7", "8", "9")) (board.formatted)
    }
  }

  describe("movesMade") {
    it("is the number of moves made") {
      board.squares = Vector.fill(9)(" ")
      expectResult(0) (board.movesMade)

      board.move(1, "x")
      expectResult(1) (board.movesMade)
    }
  }
}
