/**
 * Created by ross on 2/22/16.
 */

function ScrabbleBoard(game, boardWidth, boardHeight) {

    this.boardWidth = boardWidth;
    this.gameWidth = game.width;
    this.gameHeight = game.height;
    this.boardHeight = boardHeight;
    var boardOverview = [[]];
    (function () {

        for (var i = 0; i < 15; i++) {
            for (var j = 0; j < 15; j++) {

                boardOverview.push(null, null);
            }
        }
    })();
}

ScrabbleBoard.prototype.calculateExcessPixelsX = function () {
    return (this.gameWidth - this.boardWidth) / 2;
};

ScrabbleBoard.prototype.calculateMaxBoardWidth = function () {
    return this.gameWidth - this.calculateExcessPixelsX();
};

ScrabbleBoard.prototype.calculateExcessPixelsY = function () {
    return (this.gameHeight - this.boardHeight) / 2;
};

ScrabbleBoard.prototype.calculateMaxBoardHeight = function () {
    return this.gameHeight - this.calculateExcessPixelsY();
};

//# sourceMappingURL=ScrabbleBoard-compiled.js.map