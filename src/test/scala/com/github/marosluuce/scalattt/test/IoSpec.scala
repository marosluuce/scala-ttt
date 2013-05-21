package com.github.marosluuce.scalattt.test

import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream

import org.scalatest.FunSpec
import org.scalatest.BeforeAndAfterEach

import com.github.marosluuce.scalattt.Io

class IoSpec extends FunSpec with BeforeAndAfterEach{
  var io: Io = _

  override def beforeEach {
    io = Io()
  }

  describe("getLine") {
    it("gets a line of input from the console") {
      val input = new ByteArrayInputStream("lol".getBytes)
      Console.setIn(input)

      expectResult("lol") (io.getLine)
    }
  }

  describe("getInt") {
    it("gets an int from the console") {
      val input = new ByteArrayInputStream("1111\n".getBytes)
      Console.setIn(input)

      expectResult(1111) (io.getInt)
    }

    it("throws an exeption for invalid input") {
      val input = new ByteArrayInputStream("a\n".getBytes)
      Console.setIn(input)

      intercept[NumberFormatException] (io.getInt)
    }
  }

  describe("write") {
    it("writes text to the console") {
      val output = new ByteArrayOutputStream
      Console.setOut(output)
      io.write("lol")

      expectResult("lol") (output.toString)
    }
  }

  describe("writeLine") {
    it("writes a line to the console") {
      val output = new ByteArrayOutputStream
      Console.setOut(output)
      io.writeLine("lol")

      expectResult("lol\n") (output.toString)
    }
  }
}
