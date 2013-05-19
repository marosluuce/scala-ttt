import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream

import org.scalatest.FunSpec

import com.github.marosluuce.scalattt._

class IoSpec extends FunSpec {
  describe("getLine") {
    it("gets a line of input") {
      val io = new Io
      val input = new ByteArrayInputStream("lol".getBytes)
      Console.setIn(input)

      expectResult("lol") (io.getLine)
    }
  }

  describe("writeLine") {
    it("writes a line of") {
      val io = new Io
      val output = new ByteArrayOutputStream
      Console.setOut(output)
      io.writeLine("lol")

      expectResult("lol\n") (output.toString)
    }
  }
}
