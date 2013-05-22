package com.github.marosluuce.scalattt

import scala.util.{Try, Success, Failure}

object Cli {
  val playerOneSymbol = "x"
  val playerTwoSymbol = "o"
  val movePrompt = "Enter your move: "
  val invalidInput = "Invalid input!"
  val boardRowLength = 3
  val boardRow = " %s | %s | %s "
  val boardDividor = "---|---|---"

  def apply() = new Cli(Game(), Io())
}

class Cli(val game: Game, val io: Io) {
  val aiStrategy = () => { Ai.hard(game) }
  val humanStrategy = promptMove _

  def aiPlayer(symbol: String) = Player(symbol, aiStrategy)

  def humanPlayer(symbol: String) = Player(symbol, humanStrategy)

  def run = while (!game.gameover) (takeTurn)

  def takeTurn = {
    game.move(game.currentPlayer.requestMove, game.currentPlayer.symbol)
    printBoard
  }

  def promptMove = promptAndValidateInput(io.getInt _, Cli.movePrompt)

  def printBoard {
    val iterator = game.formattedBoard.grouped(Cli.boardRowLength)

    while (iterator.hasNext) {
      iterator.next match {
        case Vector(a, b, c) =>
          io.writeLine(Cli.boardRow.format(a, b, c))
          if (iterator.hasNext) { io.writeLine(Cli.boardDividor) }
      }
    }
  }

  private[this] def promptAndValidateInput(input: () => Int, message: String): Int = {
    io.write(message)

    Try(input()) match {
      case Success(num) => num
      case Failure(_) =>
        io.writeLine(Cli.invalidInput)
        promptAndValidateInput(input, message)
    }
  }
}
