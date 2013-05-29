package scalattt

object CliIo {
  def apply() = new CliIo
}

class CliIo extends Io {
  def getInt = readInt

  def write(text: String) = print(text)

  def writeLine(line: String) = println(line)

  def writeBlankLine = print("\n")
}
