package com.github.marosluuce.scalattt

object Player {
  def apply(symbol: String, strategy: () => Int) = new Player(symbol, strategy)
}

class Player(val symbol: String, val strategy: () => Int) {
  def requestMove = strategy()
}
