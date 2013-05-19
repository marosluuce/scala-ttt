import org.scalatest.FunSpec

import com.github.marosluuce.scalattt._

class GameSpec extends FunSpec {
  it("creates a new copy of the game") {
    expectResult(classOf[Game]) ((new Game).getClass)
  }

  it("has a board") {
    expectResult(classOf[Board]) ((new Game).board.getClass)
  }

  it("has rules") {
    expectResult(classOf[Rules]) ((new Game).rules.getClass)
  }
}
