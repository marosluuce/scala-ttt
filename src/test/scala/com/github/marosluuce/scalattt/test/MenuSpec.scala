package com.github.marosluuce.scalattt.test

import org.scalatest.FunSpec
import org.scalatest.BeforeAndAfterEach

import com.github.marosluuce.scalattt.InvalidChoiceException
import com.github.marosluuce.scalattt.Menu

import com.github.marosluuce.scalattt.test.mock.MockIo

class MenuSpec extends FunSpec with BeforeAndAfterEach {
  var i: Int = _
  var io: MockIo = _
  var choices: (Int) => Unit = _
  var errorMessage: String = _
  var prompt: String = _
  var menu: Menu = _

  override def beforeEach {
    i = 0
    io = new MockIo
    choices = (num: Int) => num match {
      case 1 => i += 1
      case _ => throw new InvalidChoiceException
    }
    errorMessage = "I AM ERROR"
    prompt = "Prompt!"
    menu = Menu(prompt, choices, io, errorMessage)
  }

  describe("apply") {
    it("runs the action") {
      io.input = List("1")
      menu()
      expectResult(1) (i)
    }

    it("prints the prompt") {
      io.input = List("1")
      menu()
      assert(io.didOutput(prompt))
    }

    it("prints an error message when it gets an Invalid Choice Exception") {
      io.input = List("2", "1")
      menu()

      assert(io.didOutput(errorMessage))
    }

    it("reruns the action when it gets an Invalid Choice Exception") {
      io.input = List("2", "1")
      menu()

      expectResult(1) (i)
    }

    it("prints an error message for invalid input") {
      io.input = List("a", "1")
      menu()

      assert(io.didOutput(Menu.invalidInput))
    }
  }
}
