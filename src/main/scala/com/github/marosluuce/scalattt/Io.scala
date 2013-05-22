package com.github.marosluuce.scalattt

object Io {
  def apply() = new Io
}

class Io {
  def getLine = readLine

  def getInt = readInt

  def write(text: String) = print(text)

  def writeLine(line: String) = println(line)

  //def clearScreen = {
    //print(27.toChar + "[2J")
    //print(27.toChar + "[;H")
  //}
}
