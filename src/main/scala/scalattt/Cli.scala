package scalattt

import scala.util.{Try, Success, Failure}

object Cli {
  val greeting = "Welcome to Tic-Tac-Toe!"
  val playerSelectTitle = "Player Select"
  val playerSelectOptions = "1. Human vs Human\n2. Human vs AI\n3. AI vs Human\n4. AI vs AI"
  val menuAndPrompt = "%s\n\n%s\n\n%s"
  val menuPrompt = "Enter your choice: "
  val drawMessage = "Draw!"
  val winMessage = "%s Won!"
  val playAgainTitle = "Play again?"
  val playAgainOptions = "1. Yes\n2. No"
  val playerOneSymbol = "x"
  val playerTwoSymbol = "o"
  val playersTurn = "%s's turn"
  val movePrompt = "Enter your move: "
  val invalidMove = "Invalid move!"
  val invalidInput = "Invalid input!"
  val invalidChoice = "Invalid choice!"
  val boardRowLength = 3
  val boardRow = " %s | %s | %s \n"
  val boardDivider = "---|---|---\n"

  val playerSelectMenu = Cli.menuAndPrompt.format(Cli.playerSelectTitle,
                                                  Cli.playerSelectOptions,
                                                  Cli.menuPrompt)
  val playAgainMenu = Cli.menuAndPrompt.format(Cli.playAgainTitle,
                                               Cli.playAgainOptions,
                                               Cli.menuPrompt)

  def apply(game: Game, io: Io) = new Cli(game, io)
}

class Cli(val game: Game, val io: Io) {
  val aiStrategy = () => Ai.hard(game)
  val humanStrategy = promptMove _
  var runPlayerSelectMenu = Menu(Cli.playerSelectMenu, createPlayers(_), io, Cli.invalidChoice)
  var runPlayAgainMenu = Menu(Cli.playAgainMenu, playAgain(_), io, Cli.invalidChoice)

  def aiPlayer(symbol: String) = Player(symbol, aiStrategy)

  def humanPlayer(symbol: String) = Player(symbol, humanStrategy)

  def run = {
    def mainLoop {
      runPlayerSelectMenu()
      while (!game.gameover) (takeTurn)
      printOutcome
      runPlayAgainMenu()
      game.gameover match {
        case false => mainLoop
        case _ =>
      }
    }
    io.writeLine(Cli.greeting)
    mainLoop
  }

  def takeTurn = turn(() => game.move(game.currentPlayer.requestMove, game.currentPlayer.symbol))

  def createPlayers(choice: Int) = choice match {
    case 1 => game.setPlayers(humanPlayer(Cli.playerOneSymbol), humanPlayer(Cli.playerTwoSymbol))
    case 2 => game.setPlayers(humanPlayer(Cli.playerOneSymbol), aiPlayer(Cli.playerTwoSymbol))
    case 3 => game.setPlayers(aiPlayer(Cli.playerOneSymbol), humanPlayer(Cli.playerTwoSymbol))
    case 4 => game.setPlayers(aiPlayer(Cli.playerOneSymbol), aiPlayer(Cli.playerTwoSymbol))
    case _ => throw new InvalidChoiceException
  }

  def playAgain(choice: Int) = choice match {
    case 1 => game.reset
    case 2 =>
    case _ => throw new InvalidChoiceException
  }

  def promptMove = promptAndValidateInput(Cli.movePrompt)

  def formattedBoard = {
    game.boardForPrint.grouped(Cli.boardRowLength).map {
      case Vector(a, b, c) => Cli.boardRow.format(a, b, c)
    }.mkString(Cli.boardDivider)
  }

  def printBoard = { io.writeBlankLine; io.write(formattedBoard) }

  def printOutcome = {
    printBoard
    io.writeBlankLine
    game.winner match {
      case Some(symbol) => io.writeLine(Cli.winMessage.format(symbol))
      case None => io.writeLine(Cli.drawMessage)
    }
  }

  private[this] def turn(move: () => Unit) {
    printBoard
    io.writeBlankLine
    io.writeLine(Cli.playersTurn.format(game.currentPlayer.symbol))

    Try(move()) match {
      case Failure(e: InvalidMoveException) =>
        io.writeLine(Cli.invalidMove)
        turn(move)
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
