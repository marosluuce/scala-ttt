package com.github.marosluuce.scalattt

object Io {
  def apply() = new Io
}

class Io {
  def getInt = readInt

  def write(text: String) = print(text)

  def writeLine(line: String) = println(line)

  def writeBlankLine = print("\n")
}
