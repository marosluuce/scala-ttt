package com.github.marosluuce.scalattt

object Main {
  def main(args: Array[String]) {
    val cli = Cli()
    cli.game.setPlayers(Player("x"), Player("o"))

    while(!cli.game.board.gameover) {
      val player = cli.game.currentPlayer
      cli.game.board.move(player.requestMove(cli.promptMove _), player.symbol)
      cli.printBoard
    }
  }
}
