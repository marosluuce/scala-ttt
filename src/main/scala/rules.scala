class Rules(game_board: Board) {
  val board = game_board

  def winnerIs(sym: String) = {
    val b = board.getBoard
    sym match {
      case symbol if s"${sym}${sym}${sym}      " == b ||
                     s"   ${sym}${sym}${sym}   " == b ||
                     s"      ${sym}${sym}${sym}" == b ||
                     s"${sym}  ${sym}  ${sym}  " == b ||
                     s" ${sym}  ${sym}  ${sym} " == b ||
                     s"  ${sym}  ${sym}  ${sym}" == b ||
                     s"${sym}   ${sym}   ${sym}" == b ||
                     s"  ${sym} ${sym} ${sym}  " == b => true
      case _ => false
      }
  }

  def gameover = board.full ||
                 (false /: board.getBoard.toList.distinct.filter(_!=' ')) {
                   case (acc, sym) => acc || winnerIs(sym.toString)
                 }

  def draw = board.full && gameover
}
