/**
 * Created by ross on 2/23/16.
 */
var InterfaceMechanics = WordsWithBytes.InterfaceMechanics;

WordsWithBytes.InterfaceMechanics = function() {};

InterfaceMechanics.prototype.sortNumber = function(a, b) {
    return a - b;
};

InterfaceMechanics.prototype.calculateRackLocations = function(game) {
    var referenceHeight = this.scrabbleBoard.calculateMaxBoardHeight();
    var originalXPosition = game.centerX;
    var yPosition = referenceHeight + ((referenceHeight - game.height) / 2);
    var rackPositionsX = [];

    for (var i = 0; i < 3; i++) {
        rackPositionsX.push(originalXPosition);
        originalXPosition = originalXPosition - game.height
    }

}

InterfaceMechanics.prototype.calculateCenterSquares = function() {

    var maxWidth = this.scrabbleBoard.calculateMaxBoardWidth();
    var excessPixelsX = this.scrabbleBoard.calculateExcessPixelsX();
    var xScaleFactor = maxWidth / 15;
    this.centerCoordinatesX = [];
    var maxHeight = this.scrabbleBoard.calculateMaxBoardHeight();
    var yScaleFactor = maxHeight / 15;
    this.centerCoordinatesY = [];

    for (var initialPixelWidth = excessPixelsX; initialPixelWidth <= maxWidth; initialPixelWidth += xScaleFactor) {
        this.centerCoordinatesX.push(initialPixelWidth);
    }

    for (var initialPixelHeight = this.scrabbleBoard.calculateExcessPixelsY(); initialPixelHeight <= maxHeight; initialPixelHeight += yScaleFactor) {
        this.centerCoordinatesY.push(initialPixelHeight + 1);
    }
};
/**
 *@ADD-FUNCTIONALITY If space already taken by already placed letter, reject that letter.
 */

InterfaceMechanics.prototype.searchForClosestSpace = function(letter, letterCordX, letterCordY) {
    var closestSquaresX = [];
    var closestSquaresY = [];

    for (var i = 0; i < 15; i++) {
        closestSquaresX.push(Math.abs(letterCordX - this.centerCoordinatesX[i]));
        closestSquaresY.push(Math.abs(letterCordY - this.centerCoordinatesY[i]));
    }
    closestSquaresX.sort(this.sortNumber);
    closestSquaresY.sort(this.sortNumber);
    letter.x = closestSquaresX[0];
    letter.y = closestSquaresY[0];
};



