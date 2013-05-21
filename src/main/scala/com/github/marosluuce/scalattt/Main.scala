package com.github.marosluuce.scalattt

object Main {
  def main(args: Array[String]) {
    val cli = Cli()

    var i = 0
    while(!cli.game.board.gameover) {
      val player = players(i)
      val move = player.requestMove(cli.promptMove _)
      cli.game.board.move(move.toInt, player.symbol)
      cli.printBoard
      i += 1
    }
  }

  def players(i: Int) = i match {
    case i if i % 2 == 0 => Player("x")
    case _ => Player("o")
  }
}
