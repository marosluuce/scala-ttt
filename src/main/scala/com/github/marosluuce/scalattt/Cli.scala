package com.github.marosluuce.scalattt

import scala.util.{Try, Success, Failure}

object Cli {
  val playerOneSymbol = "x"
  val playerTwoSymbol = "o"
  val movePrompt = "Enter your move: "
  val invalidMove = "Invalid move!"
  val invalidInput = "Invalid input!"
  val boardRowLength = 3
  val boardRow = " %s | %s | %s \n"
  val boardDivider = "---|---|---\n"

  def apply() = new Cli(Game(), Io())
}

class Cli(val game: Game, val io: Io) {
  val aiStrategy = () => { Ai.hard(game) }
  val humanStrategy = promptMove _

  def aiPlayer(symbol: String) = Player(symbol, aiStrategy)

  def humanPlayer(symbol: String) = Player(symbol, humanStrategy)

  def run = while (!game.gameover) (takeTurn)

  def takeTurn = turn(() => game.move(game.currentPlayer.requestMove,
                                      game.currentPlayer.symbol))

  def promptMove = promptAndValidateInput(io.getInt _, Cli.movePrompt)

  def formattedBoard = {
    game.boardForPrint.grouped(Cli.boardRowLength).map {
      case Vector(a, b, c) => Cli.boardRow.format(a, b, c)
    }.mkString(Cli.boardDivider)
  }

  def printBoard {
    io.write(formattedBoard)
  }

  private[this] def turn(move: () => Unit): Unit = {
    printBoard
    Try(move()) match {
      case Failure(e: InvalidMoveException) =>
        io.writeLine(Cli.invalidMove)
        turn(move)
      case _ =>
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
