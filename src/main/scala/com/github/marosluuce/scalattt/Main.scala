package com.github.marosluuce.scalattt

object Main {
  def main(args: Array[String]) {
    val cli = new Cli(new Game, new Io)

    var i = 0
    while(!cli.game.board.gameover) {
      val move = cli.promptMove
      cli.game.board.move(move.toInt, symbol(i))
      cli.io.writeLine(cli.game.board.getBoard.toString)
      i += 1
    }
  }

  def symbol(i: Int) = i match {
    case i if i % 2 == 0 => "x"
    case _ => "o"
  }
}
