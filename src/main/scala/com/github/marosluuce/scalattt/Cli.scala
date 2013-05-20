package com.github.marosluuce.scalattt

object Cli {
  val movePrompt = "Enter your move: "
  val invalidInput = "Invalid input!"
}

class Cli(val game: Game, val io: Io) {
  def promptMove: Int = {
    io.write(Cli.movePrompt)
    try { io.getInt }
    catch {
      case e: NumberFormatException =>
        io.writeLine(Cli.invalidInput)
        promptMove
    }
  }
}

// vector.zipWithIndex.map {
// case (value, _) if value != " " => value
// case (_, index) => s"${index+1}"
// }
