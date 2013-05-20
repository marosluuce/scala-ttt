package com.github.marosluuce.scalattt

object Board {
  val emptySquare = " "
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

  def winner = boardSymbols.map(winnerIs(_)).filter(_!=null).headOption

  def gameover = full || winner.nonEmpty

  def draw = full && gameover

  private[this] def squareIsEmpty(index: Int) = squares(index) == Board.emptySquare

  private[this] def boardSymbols = squares.distinct.filter(_!=Board.emptySquare)

  private[this] def winnerIs(sym: String) = squares match {
    case Vector(`sym`, `sym`, `sym`, _, _, _, _, _, _) |
         Vector(_, _, _, `sym`, `sym`, `sym`, _, _, _) |
         Vector(_, _, _, _, _, _, `sym`, `sym`, `sym`) |
         Vector(`sym`, _, _, `sym`, _, _, `sym`, _, _) |
         Vector(_, `sym`, _, _, `sym`, _, _, `sym`, _) |
         Vector(_, _, `sym`, _, _, `sym`, _, _, `sym`) |
         Vector(`sym`, _, _, _, `sym`, _, _, _, `sym`) |
         Vector(_, _, `sym`, _, `sym`, _, `sym`, _, _) => sym
    case _ => null
  }
}
