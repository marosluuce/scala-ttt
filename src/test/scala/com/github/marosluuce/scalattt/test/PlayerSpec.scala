package com.github.marosluuce.scalattt.test

import org.scalatest.FunSpec
import org.scalatest.BeforeAndAfterEach

import com.github.marosluuce.scalattt.Player

class PlayerSpec extends FunSpec with BeforeAndAfterEach {
  var player: Player = _

  override def beforeEach {
    player = Player("x")
  }

  it("has a symbol") {
    expectResult("x") (player.symbol)
  }

  describe("requestMove") {
    it("takes a function that gets a move") {
      expectResult(1) (player.requestMove(() => 1))
    }
  }
}
