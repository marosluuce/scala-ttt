package scalattt

trait Io {
  def getInt: Int

  def write(text: String): Unit

  def writeLine(line: String): Unit

  def writeBlankLine: Unit
}
