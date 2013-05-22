package com.github.marosluuce.scalattt

object Game {
  def apply() = new Game(new Board)
}

class Game(val board: Board) {
  var players = Vector[Player]()

  def formattedBoard = board.formatted

  def availableMoves = board.availableMoves

  def gameover = board.gameover

  def draw = board.draw

  def winner = board.winner

  def move(square: Int, symbol: String) = board.validMove(square) match {
    case true => board.move(square, symbol)
    case _ => throw new InvalidMoveException
  }

  def undoMove(square: Int) = board.undoMove(square)

  def setPlayers(playerOne: Player, playerTwo: Player) = players = Vector(playerOne, playerTwo)

  def currentPlayer = board.movesMade match {
    case count if count % 2 == 0 => players.head
    case _ => players.last
  }
}
