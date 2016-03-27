/**
 * Created by ross on 2/22/16.
 */

(function(WordsWithBytes){

  function ScrabbleBoard(game, boardImage) {
    this.game = game;
    this.gameWidth = this.game.width;
    this.gameHeight = this.game.height;
    this.boardWidth = boardImage.width;
    this.boardHeight = boardImage.height;
    ScrabbleBoard.scaledBoardWidth = calculateScaledBoardWidth();
    ScrabbleBoard.excessPixelsX = calculateExcessPixelsX();
    ScrabbleBoard.excessPixelsY = calculateExcessPixelsY();
  }

  function calculateExcessPixelsX() {
      return this.gameWidth - this.boardWidth / 2;
  }

  function calculateScaledBoardWidth() {
      return this.gameWidth - ScrabbleBoard.excessPixelsX;
  }

  function calculateExcessPixelsY() {
    return (this.gameHeight - this.boardHeight) / 2;
  }

  function calculateScaledBoardHeight() {
    return this.gameHeight - ScrabbleBoard.excessPixelsY;
  }

  WordsWithBytes.ScrabbleBoard = ScrabbleBoard;

})(this);
