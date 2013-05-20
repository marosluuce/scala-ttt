package com.github.marosluuce.scalattt.test.mock


import com.github.marosluuce.scalattt.Io

class MockIo extends Io {
  var input = List[String]()
  var output = List[String]()

  override def getLine = input match {
    case head :: tail => input = tail; s"${head}\n"
    case _ => "\n"
  }

  override def getInt = input match {
    case head :: tail => input = tail; head.replaceAll("\n", "").toInt
    case _ => throw new NumberFormatException
  }

  override def write(text: String) = output = output ++ List(text)

  override def writeLine(line: String) = output = output ++ List(s"${line}\n")
}
