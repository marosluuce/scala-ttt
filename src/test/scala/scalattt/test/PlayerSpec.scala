package scalattt.test

import org.scalatest.FunSpec
import org.scalatest.BeforeAndAfterEach

import scalattt.Player

class PlayerSpec extends FunSpec with BeforeAndAfterEach {
  val strategy = () => 1
  var player: Player = _

  override def beforeEach {
    player = Player("x", strategy)
  }

  it("has a symbol") {
    expectResult("x") (player.symbol)
  }

  describe("requestMove") {
    it("takes a function that gets a move") {
      expectResult(1) (player.requestMove)
    }
  }
}
