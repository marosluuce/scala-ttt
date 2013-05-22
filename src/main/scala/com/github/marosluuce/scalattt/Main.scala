package com.github.marosluuce.scalattt

object Main {
  def main(args: Array[String]) {
    val cli = Cli()
    cli.game.setPlayers(cli.humanPlayer("x"), cli.aiPlayer("o"))

    cli.run
  }
}
