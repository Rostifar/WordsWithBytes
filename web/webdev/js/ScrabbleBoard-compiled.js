/**
 * Created by ross on 2/22/16.
 */

(function (WordsWithBytes) {

  function ScrabbleBoard(game, boardImage) {
    this.gameWidth = game.width;
    this.gameHeight = game.height;
    this.boardWidth = boardImage.width;
    this.boardHeight = boardImage.height;
    this.scaledBoardWidth = calculateScaledBoardWidth();
    this.excessPixelsX = calculateExcessPixelsX();
    this.excessPixelsY = calculateExcessPixelsY();
    this.scaledBoardHeight = calculateScaledBoardHeight();
  }

  function calculateExcessPixelsX() {
    return this.gameWidth - this.boardWidth / 2;
  }

  function calculateScaledBoardWidth() {
    return this.gameWidth - this.excessPixelsX;
  }

  function calculateExcessPixelsY() {
    return (this.gameHeight - this.boardHeight) / 2;
  }

  function calculateScaledBoardHeight() {
    return this.gameHeight - this.excessPixelsY;
  }

  WordsWithBytes.ScrabbleBoard = ScrabbleBoard;
})(this);

//# sourceMappingURL=ScrabbleBoard-compiled.js.map