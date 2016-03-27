
(function(WordsWithBytes) {

    function InterfaceMechanics(game, boardImage) {
        InterfaceMechanics.boardWidth = WordsWithBytes.ScrabbleBoard.scaledBoardWidth;
        InterfaceMechanics.centerSquaresX = [];
        InterfaceMechanics.centerSquaresY = [];
        calculateCenterSquares();
    }

    function calculateCenterSquares() {
        var xScaleFactor = WordsWithBytes.ScrabbleBoard.scaledBoardWidth / 15;
        var yScaleFactor = WordsWithBytes.ScrabbleBoard.scaledBoardHeight / 15;
        var excessPixelsX = WordsWithBytes.ScrabbleBoard.excessPixelsX;
        var excessPixelsY = WordsWithBytes.ScrabbleBoard.excessPixelsY;

        for (var initialPixelWidth = excessPixelsX; initialPixelWidth <= maxWidth; initialPixelWidth += xScaleFactor) {
            InterfaceMechanics.centerSquaresX.push(initialPixelWidth);
        }

        for (var initialPixelHeight = excessPixelsY; initialPixelHeight <= maxHeight; initialPixelHeight += yScaleFactor) {
            InterfaceMechanics.centerSquaresY.push(initialPixelHeight);
        }
    }

    function sortNumber(a,b) {
        return a - b;
    }

    InterfaceMechanics.prototype.isInBoardProximity = function(pointer) {
        var minimumWidth = WordsWithBytes.ScrabbleBoard.excessPixelsX;
        var minimumHeight = WordsWithBytes.ScrabbleBoard.excessPixelsY;
        var maximumHeight = WordsWithBytes.ScrabbleBoard.scaledBoardHeight;
        var maximumWidth = WordsWithBytes.ScrabbleBoard.scaledBoardWidth;

        return ((pointer.x >= minimumWidth && pointer.x <= maximumWidth) && (pointer.y >= minimumHeight && pointer.y <= maximumHeight));
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