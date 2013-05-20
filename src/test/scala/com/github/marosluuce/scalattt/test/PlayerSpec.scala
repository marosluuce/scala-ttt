package com.github.marosluuce.scalattt.test

import org.scalatest.FunSpec

import com.github.marosluuce.scalattt.Player

class PlayerSpec extends FunSpec {
  it("has a symbol") {
    val player = new Player("x")
    expectResult("x") (player.symbol)
  }
}
