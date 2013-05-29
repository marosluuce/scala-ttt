package scalattt.test.mock

import scalattt.Io

class MockIo extends Io {
  var input = List[String]()
  var output = List[String]()

  def getInt = input match {
    case head :: tail => input = tail; head.replaceAll("\n", "").toInt
    case _ => throw new NumberFormatException
  }

  def write(text: String) = output = output ++ List(text)

  def writeLine(line: String) = output = output ++ List(s"${line}\n")

  def writeBlankLine = output = output ++ List("\n")

  def didOutput(expected: String) = {
    (false /: output) { case (acc, line) => acc || line.contains(expected) }
  }
}
