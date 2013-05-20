package com.github.marosluuce.scalattt

object Main {
  def main(args: Array[String]) {
    val cli = new Cli(new Game, new Io)

    while(!cli.game.board.gameover) {
      val move = cli.promptMove
      cli.game.board.move(move.toInt, "x")
      cli.io.writeLine(cli.game.board.getBoard.toString)
    }
  }
}
