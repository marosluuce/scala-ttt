package com.github.marosluuce.scalattt.test.mock

import com.github.marosluuce.scalattt.Io
import com.github.marosluuce.scalattt.Menu

class MockMenu(choices: () => Unit, io: Io) extends Menu("", (x: Int) => choices(), io, "") {
  override def apply() = choices()
}
