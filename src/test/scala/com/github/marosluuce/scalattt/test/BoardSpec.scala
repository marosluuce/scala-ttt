package com.github.marosluuce.scalattt.test

import org.scalatest.FunSpec

import com.github.marosluuce.scalattt.Board

class BoardSpec extends FunSpec {
  describe("move") {
    it("moves by setting a square to a value") {
      val board = new Board
      expectResult(" ") (board.squares(0))

      board.move(1, "x")
      expectResult("x") (board.squares(0))
    }
  }

  describe("undoMove") {
    it("undoes a move") {
      val board = new Board
      board.move(1, "x")
      board.undoMove(1)

      expectResult(" ") (board.squares(0))
    }
  }

  describe("getBoard") {
    it("gets the board") {
      val board = new Board
      expectResult(Vector(" ", " ", " ", " ", " ", " ", " ", " ", " ")) (board.squares)

      for(i <- 1 to 9) { board.move(i, "x") }
      expectResult(Vector("x", "x", "x", "x", "x", "x", "x", "x", "x")) (board.squares)
    }
  }

  describe("getAvailableSquares") {
    it("gets the available squares") {
      val board = new Board
      expectResult(Vector(1, 2, 3, 4, 5, 6, 7, 8, 9)) (board.getAvailableSquares)

      board.move(1, "x")
      expectResult(Vector(2, 3, 4, 5, 6, 7, 8, 9)) (board.getAvailableSquares)
    }
  }

  describe("validMove") {
    it("is valid of the square is available") {
      val board = new Board

      expectResult(true) (board.validMove(1))
    }

    it("is invalid if the square is not available") {
      val board = new Board
      board.move(1, "x")

      expectResult(false) (board.validMove(1))
    }
  }

  describe("full") {
    it("is false if all the squares are not filled") {
      val board = new Board
      expectResult(false) (board.full)

      board.move(1, "x")
      expectResult(false) (board.full)
    }

    it("is true if all the squares are filled") {
      val board = new Board
      for (i <- 1 to 9) { board.move(i, "x") }

      expectResult(true) (board.full)
    }
  }

  describe("winnerIs") {
    it("is true for 'xxx      '") {
      val board = new Board
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

    it("is false for 'xxo'") {
      val board = new Board
      board.squares = Vector("x", "x", "o", " ", " ", " ", " ", " ", " ")

      expectResult(None) (board.winner)
    }
  }

  describe("gameover") {
    it("is true if the board is full") {
      val board = new Board
      board.squares = Vector("x", "o", "x", "x", "o", "x", "x", "o", "x")

      expectResult(true) (board.gameover)
    }

    it("is true if there is a winner") {
      val board = new Board
      board.squares = Vector("x", "o", "x", "x", "o", "x", "x", "o", "x")

      expectResult(true) (board.gameover)
    }

    it("is false if there is no winner and the board is not full") {
      val board = new Board
      board.squares = Vector("x", "x", "o", " ", " ", " ", " ", " ", " ")

      expectResult(false) (board.gameover)
    }
  }

  describe("draw") {
    it("is false if the board is not full") {
      val board = new Board

      expectResult(false) (board.draw)
    }

    it("is false if there is a winner") {
      val board = new Board
      board.squares = Vector("x", "x", "x", " ", " ", " ", " ", " ", " ")

      expectResult(false) (board.draw)
    }

    it("is true if the board is full and there is no winner") {
      val board = new Board
      board.squares = Vector("x", "o", "x", "x", "o", "x", "o", "x", "o")

      expectResult(true) (board.draw)
    }
  }
}
