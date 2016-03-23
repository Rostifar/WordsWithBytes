/**
 * Created by ross on 2/22/16.
 */
var ScrabbleBoard = WordsWithBytes.ScrabbleBoard;

ScrabbleBoard = {
    s
}

WordsWithBytes.ScrabbleBoard = function(game, boardImage) {};

ScrabbleBoard.prototype = {

}

var calculateExcessPixelsX = (function() {
    this.gameWidth - this.boardWidth / 2;
})();


ScrabbleBoard.prototype.calculateMaxBoardWidth = function() {
    return this.gameWidth - this.calculateExcessPixelsX();
};

ScrabbleBoard.prototype.calculateExcessPixelsY = function() {
    return (this.gameHeight - this.boardHeight) / 2;
};

ScrabbleBoard.prototype.calculateMaxBoardHeight = function() {
    return this.gameHeight - this.calculateExcessPixelsY();
};











