class Rules(val board: Board) {
  def winnerIs(sym: String) = board.getBoard match {
    case Vector(`sym`, `sym`, `sym`, _, _, _, _, _, _) |
         Vector(_, _, _, `sym`, `sym`, `sym`, _, _, _) |
         Vector(_, _, _, _, _, _, `sym`, `sym`, `sym`) |
         Vector(`sym`, _, _, `sym`, _, _, `sym`, _, _) |
         Vector(_, `sym`, _, _, `sym`, _, _, `sym`, _) |
         Vector(_, _, `sym`, _, _, `sym`, _, _, `sym`) |
         Vector(`sym`, _, _, _, `sym`, _, _, _, `sym`) |
         Vector(_, _, `sym`, _, `sym`, _, `sym`, _, _) => true
    case _ => false
  }

  def gameover = board.full ||
                 (false /: board.getBoard.distinct.filter(_!=" ")) {
                   case (acc, sym) => acc || winnerIs(sym.toString)
                 }

  def draw = board.full && gameover
}
