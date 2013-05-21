package com.github.marosluuce.scalattt

object Board {
  val emptySquare = " "

  def apply() = new Board
}

class Board {
  var squares = Vector.fill(9)(Board.emptySquare)

  def move(square: Int, value: String) = squares = squares.updated(square-1, value)

  def undoMove(square: Int) = squares = squares.updated(square-1, Board.emptySquare)

  def getBoard = squares.toVector

  def getAvailableSquares = {
    (Vector[Int]() /: squares.zipWithIndex) {
      case (acc, (Board.emptySquare, index)) => acc ++ Vector(index+1)
      case (acc, _) => acc
    }
  }

  def validMove(square: Int) = getAvailableSquares.contains(square)

  def full = !squares.contains(Board.emptySquare)

  def winner = Option(winnerIs)

  def gameover = full || winner.nonEmpty

  def draw = full && gameover

  def formatted = squares.zipWithIndex.map {
    case (sym, _) if sym != Board.emptySquare => sym
    case (_, index) => s"${index+1}"
  }

  private[this] def squareIsEmpty(index: Int) = squares(index) == Board.emptySquare

  private[this] def winnerIs = squares match {
    case Vector(x, y, z, _, _, _, _, _, _) if sameSymbol(x, y, z) => x
    case Vector(_, _, _, x, y, z, _, _, _) if sameSymbol(x, y, z) => x
    case Vector(_, _, _, _, _, _, x, y, z) if sameSymbol(x, y, z) => x
    case Vector(x, _, _, y, _, _, z, _, _) if sameSymbol(x, y, z) => x
    case Vector(_, x, _, _, y, _, _, z, _) if sameSymbol(x, y, z) => x
    case Vector(_, _, x, _, _, y, _, _, z) if sameSymbol(x, y, z) => x
    case Vector(x, _, _, _, y, _, _, _, z) if sameSymbol(x, y, z) => x
    case Vector(_, _, x, _, y, _, z, _, _) if sameSymbol(x, y, z) => x
    case _ => null
  }

  private[this] def sameSymbol(x: Any, y: Any, z: Any) =
    x != Board.emptySquare && x == y && x == z
}
