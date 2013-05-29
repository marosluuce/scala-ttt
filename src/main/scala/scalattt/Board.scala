package scalattt

object Board {
  val emptySquare = " "
  val emptyBoard = Vector.fill(9)(Board.emptySquare)

  def apply() = new Board
}

class Board {
  var squares = Board.emptyBoard

  def reset = squares = Board.emptyBoard

  def move(square: Int, value: String) = squares = squares.updated(square-1, value)

  def undoMove(square: Int) = squares = squares.updated(square-1, Board.emptySquare)

  def getBoard = squares.toVector

  def availableMoves = {
    (Vector[Int]() /: squares.zipWithIndex) {
      case (acc, (Board.emptySquare, index)) => acc ++ Vector(index+1)
      case (acc, _) => acc
    }
  }

  def validMove(square: Int) = availableMoves.contains(square)

  def full = !squares.contains(Board.emptySquare)

  def winner = Option(winnerIs)

  def gameover = full || winner.nonEmpty

  def formatted = squares.zipWithIndex.map {
    case (sym, _) if sym != Board.emptySquare => sym
    case (_, index) => s"${index+1}"
  }

  def movesMade = squares.count(_!=Board.emptySquare)

  private[this] def winnerIs = squares match {
    case Vector(x, y, z, _, _, _, _, _, _) if sameSymbol(x, y, z) => x
    case Vector(_, _, _, x, y, z, _, _, _) if sameSymbol(x, y, z) => x
    case Vector(_, _, _, _, _, _, x, y, z) if sameSymbol(x, y, z) => x
    case Vector(x, _, _, y, _, _, z, _, _) if sameSymbol(x, y, z) => x
    case Vector(_, x, _, _, y, _, _, z, _) if sameSymbol(x, y, z) => x
    case Vector(_, _, x, _, _, y, _, _, z) if sameSymbol(x, y, z) => x
    case Vector(x, _, _, _, y, _, _, _, z) if sameSymbol(x, y, z) => x
    case Vector(_, _, x, _, y, _, z, _, _) if sameSymbol(x, y, z) => x
    case _ => null
  }

  private[this] def sameSymbol(x: Any, y: Any, z: Any) =
    x != Board.emptySquare && x == y && x == z
}
