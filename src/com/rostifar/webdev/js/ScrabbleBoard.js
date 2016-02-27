/**
 * Created by ross on 2/22/16.
 */


function ScrabbleBoard(game) {

    this.scrabbleBoard = game.add.image(game.world.centerX, game.world.centerY, 'scrabbleBoard');
    this.boardWidth = this.scrabbleBoard.width;
    this.gameWidth = game.width;
    this.gameHeight = game.height;
    this.boardHeight = this.scrabbleBoard.height;
    this.scrabbleBoard.anchor.setTo(0.5);


}

ScrabbleBoard.prototype.calculateExcessPixelsX = function() {
    return (this.gameWidth - this.boardWidth) / 2;
};

ScrabbleBoard.prototype.calculateMaxBoardWidth = function() {
    return this.gameWidth - this.calculateMaxBoardWidth();
};

ScrabbleBoard.prototype.calculateExcessPixelsY = function() {
    return (this.gameHeight - this.boardHeight) / 2;
};

ScrabbleBoard.prototype.calculateMaxBoardHeight = function() {
    return this.gameHeight - this.calculateExcessPixelsY();
};

ScrabbleBoard.prototype.boardObject = function() {
    return this.scrabbleBoard;
};
