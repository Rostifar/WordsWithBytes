
var interfaceMechanics = (function(){
    var centerSquaresX = [];
    var centerSquaresY = [];

    function calculateCenterSquares() {
        var xScaleFactor = scrabbleBoard.scaledBoardWidth / 15;
        var yScaleFactor = scrabbleBoard.scaledBoardHeight / 15;
        var excessPixelsX = scrabbleBoard.excessPixelsX;
        var excessPixelsY = scrabbleBoard.excessPixelsY;
        var scrabbleBoardHeight = scrabbleBoard.scaledBoardHeight;
        var scrabbleBoardWidth = scrabbleBoard.scaledBoardWidth;

        for (var initialPixelWidth = excessPixelsX; initialPixelWidth <= scrabbleBoardWidth; initialPixelWidth += xScaleFactor) {
            centerSquaresX.push(initialPixelWidth);
        }

        for (var initialPixelHeight = excessPixelsY; initialPixelHeight <= scrabbleBoardHeight; initialPixelHeight += yScaleFactor) {
            centerSquaresY.push(initialPixelHeight);
        }
    }

    function sortNumber(a, b) {
        return a - b;
    }

    return {

        //initCenterSquares: calculateCenterSquares(),

        searchForClosestSquare: function(letter) {
            var closestSquaresX = [];
            var closestSquaresY = [];

            for (var i = 0; i < 15; i++) {
                closestSquaresX.push(Math.abs(letter.x - centerSquaresX[i]));
                closestSquaresY.push(Math.abs(letter.y - centerSquaresY[i]));
            }
            closestSquaresX.sort(sortNumber);
            closestSquaresY.sort(sortNumber);
            letter.x = closestSquaresX[0];
            letter.y = closestSquaresY[0];
        }
    }
})();

