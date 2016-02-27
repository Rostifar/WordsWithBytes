/**
 * Created by ross on 1/31/16.
 */

WordsWithBytes.Game = function (game) {};

WordsWithBytes.Game.prototype = {

    sortNumber: function (a, b) {
        return a - b;
    },

    createLetters: function () {

        var me = this;
    },

    calculateCenterCoordinates: function (boardHeight, boardWidth, excessPixelsY, excessPixelsX) {

        var me = this;

        var yScaleFactor = this.scrabbleBoard.height / 15;
        var xScaleFactor = this.scrabbleBoard.width / 15;
        this.centerCoordinatesX = [];
        this.centerCoordinatesY = [];

        for (var initialPixelHeight = excessPixelsY; initialPixelHeight <= boardHeight; initialPixelHeight += yScaleFactor) {
            this.centerCoordinatesY.push(initialPixelHeight + 1);
        }
        for (var initialPixelWidth = excessPixelsX; initialPixelWidth <= boardWidth; initialPixelWidth += xScaleFactor) {
            this.centerCoordinatesX.push(initialPixelWidth);
        }
    },

    searchForClosestLetter: function () {

        var closestSquaresX = [];
        var closestSquaresY = [];

        for (var i = 0; i < 15; i++) {
            closestSquaresX.push(Math.abs(this.pointerX - this.centerCoordinatesX[i]));
            closestSquaresY.push(Math.abs(this.pointerY - this.centerCoordinatesY[i]));
        }

        closestSquaresX.sort(this.sortNumber);
        closestSquaresY.sort(this.sortNumber);
        this.testLetter.x = closestSquaresX[0];
        this.testLetter.y = closestSquaresY[0];
    },

    /** @update
     * Uses mouse position to calculate where to place letter on board.
     * Calculates ScrabbleBoard's actual dimensions in terms of canvas.
     * Helps "snap" letter into appropriate place through use of each space's center.
     */
    // calculatePointerLocation: function() {

    //   this.calculateCenterCoordinates(maxHeight, maxWidth, excessPixelsY, excessPixelsX);

    // if ((this.currentLetter.x >= excessPixelsX && this.currentLetter.x <= maxWidth) && (this.currentLetter.y >= excessPixelsY && this.currentLetter.y <= maxHeight) && this.currentLetter.input.onUp) {
    //            this.searchForClosestLetter();

    //          }
    //        },

    create: function () {

        var scrabbleBoard = new ScrabbleBoard(this.game);

        this.game.add.sprite(0, 0, 'space-background');
        this.scrabbleBoard.anchor.setTo(0.5);
        this.testLetter = this.add.sprite(0, 0, 'blankLetter');
        this.currentLetter = this.testLetter;

        this.testLetter.inputEnabled = true;
        this.testLetter.input.enableDrag(true);
    },

    update: function () {

        this.pointerX = this.game.input.x;
        this.pointerY = this.game.input.y;
        // this.calculatePointerLocation();
    }

};

//# sourceMappingURL=Game-compiled.js.map