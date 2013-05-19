package com.github.marosluuce.scalattt

import scala.collection.mutable._

object Board {
  private val emptySquare = " "
}

class Board {
  private val squares = Buffer.fill(9)(Board.emptySquare)

  def move(square: Int, value: String) = squares.update(square-1, value)

  def undoMove(square: Int) = squares.update(square-1, Board.emptySquare)

  def getBoard = squares.toVector

  def getAvailableSquares = {
    (Vector[Int]() /: squares.zipWithIndex) {
      case (acc, (_, index)) if squareIsEmpty(index) => acc ++ Vector(index+1)
      case (acc, _) => acc
    }
  }

  def validMove(square: Int) = getAvailableSquares.contains(square)

  def full = !squares.contains(Board.emptySquare)

  private def squareIsEmpty(index: Int) = squares(index) == Board.emptySquare
}
