import com.github.marosluuce.scalattt.Board

class MockBoard extends Board {
  var currentBoard = Vector[String]()
  var isFull = false

  override def getBoard = currentBoard
  override def full = isFull
}
