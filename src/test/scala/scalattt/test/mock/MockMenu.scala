package scalattt.test.mock

import scalattt.Io
import scalattt.Menu

class MockMenu(choices: () => Unit, io: Io) extends Menu("", (x: Int) => choices(), io, "") {
  override def apply() = choices()
}
