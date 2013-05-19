class Rules(val board: Board) {
  def winnerIs(symbol: String) = {
    val s = symbol.charAt(0)
    board.getBoard.toVector match {
      case Vector(`s`, `s`, `s`, _, _, _, _, _, _) |
           Vector(_, _, _, `s`, `s`, `s`, _, _, _) |
           Vector(_, _, _, _, _, _, `s`, `s`, `s`) |
           Vector(`s`, _, _, `s`, _, _, `s`, _, _) |
           Vector(_, `s`, _, _, `s`, _, _, `s`, _) |
           Vector(_, _, `s`, _, _, `s`, _, _, `s`) |
           Vector(`s`, _, _, _, `s`, _, _, _, `s`) |
           Vector(_, _, `s`, _, `s`, _, `s`, _, _) => true
      case _ => false
      }
  }

  def gameover = board.full ||
                 (false /: board.getBoard.toList.distinct.filter(_!=' ')) {
                   case (acc, sym) => acc || winnerIs(sym.toString)
                 }

  def draw = board.full && gameover
}
