/**
 * Created by ross on 2/22/16.
 */

(function(WordsWithBytes){

  function ScrabbleBoard(game, boardImage) {
    ScrabbleBoard.gameWidth = game.width;
    ScrabbleBoard.gameHeight = game.height;
    ScrabbleBoard.boardWidth = boardImage.width;
    ScrabbleBoard.boardHeight = boardImage.height;
  }


  ScrabbleBoard.prototype = {

    excessPixelX: (ScrabbleBoard.gameWidth -  ScrabbleBoard.boardWidth / 2),
    excessPixelsY: (ScrabbleBoard.gameHeight - ScrabbleBoard.boardHeight / 2),
    scaledBoardHeight: ScrabbleBoard.gameHeight - ScrabbleBoard.excessPixelsY,
    scaledBoardWidth: ScrabbleBoard.gameWidth - ScrabbleBoard.excessPixelX
  };

  WordsWithBytes.ScrabbleBoard = ScrabbleBoard;

})(this);
