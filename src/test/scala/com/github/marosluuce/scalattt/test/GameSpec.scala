package com.github.marosluuce.scalattt.test

import org.scalatest.FunSpec

import com.github.marosluuce.scalattt.Board
import com.github.marosluuce.scalattt.Game

class GameSpec extends FunSpec {
  it("creates a new copy of the game") {
    expectResult(classOf[Game]) ((new Game).getClass)
  }

  it("has a board") {
    expectResult(classOf[Board]) ((new Game).board.getClass)
  }
}
