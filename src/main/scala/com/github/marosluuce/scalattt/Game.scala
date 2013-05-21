package com.github.marosluuce.scalattt

object Game {
  def apply() = new Game(new Board)
}

class Game(val board: Board) {
  def formattedBoard = board.formatted
}
