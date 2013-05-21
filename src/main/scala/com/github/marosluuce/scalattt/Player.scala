package com.github.marosluuce.scalattt

object Player {
  def apply(symbol: String) = new Player(symbol)
}

class Player(val symbol: String) {
  def requestMove(strategy: () => Int) = strategy()
}
