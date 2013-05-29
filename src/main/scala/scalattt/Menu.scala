package scalattt

import scala.util.{Try, Success, Failure}

object Menu {
  val invalidInput = "Invalid input!"

  def apply(prompt: String, options: (Int) => Unit, io: Io, errorMessage: String) =
    new Menu(prompt, options, io, errorMessage)
}

class Menu(val prompt: String, val options: (Int) => Unit, val io: Io, val errorMessage: String ) {
  def apply() = runMenu(() => options(promptAndValidateInput(prompt)))

  private[this] def runMenu(action: () => Unit) {
    io.writeBlankLine

    Try(action()) match {
      case Failure(e: InvalidChoiceException) =>
        io.writeLine(errorMessage)
        runMenu(action)
      case _ =>
    }
  }

  private[this] def promptAndValidateInput(message: String): Int = {
    io.write(message)

    Try(io.getInt) match {
      case Success(num: Int) => num
      case Failure(_) =>
        io.writeLine(Menu.invalidInput)
        promptAndValidateInput(message)
    }
  }
}
