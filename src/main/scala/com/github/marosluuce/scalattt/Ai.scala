package com.github.marosluuce.scalattt

object Ai {
  def hard(game: Game) = negamax(game) match { case (square, _) => square }

  private[this] def negamax(game: Game, depth: Double = 1.0): (Int, Double) =
    game.gameover match {
      case true => (-1, score(game))
      case _ =>
        var square = -1
        var value = -999.0
        for (move <- game.availableMoves) {
          game.move(move, game.currentPlayer.symbol)
          negamax(game, depth+1) match {
            case (_, newValue: Double) if -newValue > value =>
              square = move
              value = -newValue
            case _ =>
          }
          game.undoMove(move)
        }
        (square, value/depth)
    }

  private[this] def score(game: Game) = {
    val currentSymbol = game.currentPlayer.symbol
    game.winner match {
      case None => 0
      case Some(currentSymbol) => -1
      case _ => 1
    }
  }
}
