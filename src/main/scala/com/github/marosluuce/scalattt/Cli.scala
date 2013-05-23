package com.github.marosluuce.scalattt

import scala.util.{Try, Success, Failure}

object Cli {
  val greeting = "Welcome to Tic-Tac-Toe!"
  val playerMenuOptions = "1. Human vs Human\n2. Human vs AI\n3. AI vs Human\n4. AI vs AI"
  val menuAndPrompt = "%s\n\n%s"
  val menuPrompt = "Enter your choice: "
  val drawMessage = "Draw!"
  val winMessage = "%s Won!"
  val playAgainOptions = "Play again?\n\n1. Yes\n2. No"
  val playerOneSymbol = "x"
  val playerTwoSymbol = "o"
  val movePrompt = "Enter your move: "
  val invalidMove = "Invalid move!"
  val invalidInput = "Invalid input!"
  val boardRowLength = 3
  val boardRow = " %s | %s | %s "
  val boardDivider = "---|---|---"

  val playerSelectMenu = Cli.menuAndPrompt.format(Cli.playerMenuOptions, Cli.menuPrompt)
  val playAgainMenu = Cli.menuAndPrompt.format(Cli.playAgainOptions, Cli.menuPrompt)

  def apply() = new Cli(Game(), Io())
}

class Cli(val game: Game, val io: Io) {
  val aiStrategy = () => Ai.hard(game)
  val humanStrategy = promptMove _
  var playAgainFlag = false

  def aiPlayer(symbol: String) = Player(symbol, aiStrategy)

  def humanPlayer(symbol: String) = Player(symbol, humanStrategy)

  def run = {
    def mainLoop {
      //runPlayerSelectMenu
      while (!game.gameover) (takeTurn)
      //printOutcome
      //runPlayAgainMenu
      //playAgainFlag match {
        //case true => mainLoop
        //case _ =>
      //}
    }
    io.write(Cli.greeting)
    mainLoop
  }

  def takeTurn = turn(() => game.move(game.currentPlayer.requestMove,
                                      game.currentPlayer.symbol))

  def runPlayerSelectMenu = runMenu(() => createPlayers(promptPlayerSelect))

  def runPlayAgainMenu = runMenu(() => playAgain(promptPlayAgain))

  def createPlayers(choice: Int) = choice match {
    case 1 => game.setPlayers(humanPlayer(Cli.playerOneSymbol), humanPlayer(Cli.playerTwoSymbol))
    case 2 => game.setPlayers(humanPlayer(Cli.playerOneSymbol), aiPlayer(Cli.playerTwoSymbol))
    case 3 => game.setPlayers(aiPlayer(Cli.playerOneSymbol), humanPlayer(Cli.playerTwoSymbol))
    case 4 => game.setPlayers(aiPlayer(Cli.playerOneSymbol), aiPlayer(Cli.playerTwoSymbol))
    case _ => throw new InvalidChoiceException
  }

  def playAgain(choice: Int) = choice match {
    case 1 => playAgainFlag = true
    case 2 => playAgainFlag = false
    case _ => throw new InvalidChoiceException
  }

  def promptPlayerSelect = promptAndValidateInput(Cli.playerSelectMenu)

  def promptMove = promptAndValidateInput(Cli.movePrompt)

  def promptPlayAgain = promptAndValidateInput(Cli.playAgainMenu)

  def formattedBoard = {
    game.boardForPrint.grouped(Cli.boardRowLength).map {
      case Vector(a, b, c) => Cli.boardRow.format(a, b, c) + "\n"
    }.mkString(Cli.boardDivider + "\n")
  }

  def printBoard = io.write(formattedBoard)

  def printOutcome = {
    printBoard
    game.winner match {
      case Some(symbol) => io.writeLine(Cli.winMessage.format(symbol))
      case None => io.writeLine(Cli.drawMessage)
    }
  }

  private[this] def turn(move: () => Unit) {
    printBoard

    Try(move()) match {
      case Failure(e: InvalidMoveException) =>
        io.writeLine(Cli.invalidMove)
        turn(move)
      case _ =>
    }
  }

  private[this] def runMenu(action: () => Unit) {
    Try(action()) match {
      case Failure(e: InvalidChoiceException) =>
        io.writeLine(Cli.invalidInput)
        runMenu(action)
      case _ =>
    }
  }

  private[this] def promptAndValidateInput(message: String): Int = {
    io.write(message)

    Try(io.getInt) match {
      case Success(num: Int) => num
      case Failure(_) =>
        io.writeLine(Cli.invalidInput)
        promptAndValidateInput(message)
    }
  }
}
