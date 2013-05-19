import org.scalatest.FunSpec

class GameSpec extends FunSpec {
  it("creates a new copy of the game") {
    expectResult(classOf[Game]) ((new Game).getClass)
  }

  it("has a board") {
    expectResult(classOf[Board]) ((new Game).board.getClass)
  }
}
