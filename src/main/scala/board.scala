import scala.collection.mutable._

class Board {
  private var squares = Buffer.fill(9)(" ")

  def move(square: Int, value: String) = {
    squares.update(square-1, value)
  }

  def getBoard = ("" /: squares)(_+_)

  def getAvailableSquares = {
    (Vector[Int]() /: squares.zipWithIndex) {
      case (acc, (value, index)) if value == " " => acc ++ Vector(index+1)
      case (acc, _) => acc
    }
  }

  def validMove(square: Int) = getAvailableSquares.contains(square)
}
