
(function(WordsWithBytes) {

    function InterfaceMechanics() {
        InterfaceMechanics.boardWidth = WordsWithBytes.ScrabbleBoard.scaledBoardWidth;
        InterfaceMechanics.centerSquaresX = [];
        InterfaceMechanics.centerSquaresY = [];
    }

    InterfaceMechanics.prototype.calculateCenterSquares = function() {
        var xScaleFactor = this.scrabbleBoardInstance.scaledBoardWidth / 15;
        var yScaleFactor = this.scrabbleBoardInstance.scaledBoardHeight / 15;
        var excessPixelsX = this.scrabbleBoardInstance.excessPixelX;
        var excessPixelsY = this.scrabbleBoardInstance.excessPixelsY;
        var scrabbleBoardHeight = this.scrabbleBoardInstance.scaledBoardHeight;
        var scrabbleBoardWidth = this.scrabbleBoardInstance.scaledBoardWidth;

        for (var initialPixelWidth = excessPixelsX; initialPixelWidth <= scrabbleBoardWidth; initialPixelWidth += xScaleFactor) {
            InterfaceMechanics.centerSquaresX.push(initialPixelWidth);
        }

        for (var initialPixelHeight = excessPixelsY; initialPixelHeight <= scrabbleBoardHeight; initialPixelHeight += yScaleFactor) {
            InterfaceMechanics.centerSquaresY.push(initialPixelHeight);
        }
    };

    function sortNumber(a,b) {
        return a - b;
    }

    InterfaceMechanics.prototype.isInBoardProximity = function(currentLetter) {
        var minimumWidth = this.scrabbleBoardInstance.excessPixelX;
        var minimumHeight = this.scrabbleBoardInstance.excessPixelsY;
        var maximumHeight = this.scrabbleBoardInstance.scaledBoardHeight;
        var maximumWidth = this.scrabbleBoardInstance.scaledBoardWidth;

        return ((currentLetter.x >= minimumWidth && currentLetter.x <= maximumWidth) && (currentLetter.y >= minimumHeight && currentLetter.y <= maximumHeight));
    };

    InterfaceMechanics.prototype.getScrabbleBoardInstance = function(scrabbleBoardInstance) {
        this.scrabbleBoardInstance = scrabbleBoardInstance;
    };

    InterfaceMechanics.prototype.searchForClosestSquare = function(letter) {
        var closestSquaresX = [];
        var closestSquaresY = [];

        for (var i = 0; i < 15; i++) {
            closestSquaresX.push(Math.abs(letter.x - InterfaceMechanics.centerSquaresX[i]));
            closestSquaresY.push(Math.abs(letter.y - InterfaceMechanics.centerSquaresY[i]));
        }
        closestSquaresX.sort(sortNumber);
        closestSquaresY.sort(sortNumber);
        letter.x = closestSquaresX[0];
        letter.y = closestSquaresY[0];
    };
    WordsWithBytes.InterfaceMechanics = InterfaceMechanics;
})(this);