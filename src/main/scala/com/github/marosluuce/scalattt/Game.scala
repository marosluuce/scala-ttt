package com.github.marosluuce.scalattt

object Game {
  def apply() = new Game(new Board)
}

class Game(val board: Board) {
  var players: Vector[Player] = Vector()

  def formattedBoard = board.formatted

  def move(square: Int, symbol: String) = board.validMove(square) match {
    case true => board.move(square, symbol)
    case _ => throw new InvalidMoveException
  }

  def setPlayers(p1: Player, p2: Player) = players = Vector(p1, p2)

  def currentPlayer = board.movesMade match {
    case count if count % 2 == 0 => players.head
    case _ => players.last
  }
}
