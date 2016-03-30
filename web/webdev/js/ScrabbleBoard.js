/**
 * Created by ross on 3/29/16.
 */

var scrabbleBoard = (function() {
    var scrabbleBoardImage, game;

    function getBoardImage(boardImage) {
        scrabbleBoardImage = boardImage;
    }

    function getGameInstance(gameInstance) {
        game = gameInstance;
    }

    function calculateExcessPixelsX() {
        return (game.width - scrabbleBoardImage.width) / 2;
    }

    function calculateExcessPixelsY() {
        return (game.height - scrabbleBoardImage.height) / 2;
    }

    function calculateScaledBoardHeight() {
        return game.height - calculateExcessPixelsY();
    }

    function calculateScaledBoardWidth() {
        return game.width - calculateExcessPixelsX();
    }

    return {
        getScrabbleBoardImage: function (scrabbleBoardImage) {
            getBoardImage(scrabbleBoardImage);
        },
        getScrabbleGameInstance: function(gameInstance) {
            getGameInstance(gameInstance);
        },
        excessPixelsX: calculateExcessPixelsX(),
        excessPixelsY: calculateExcessPixelsY(),
        scaledBoardHeight: calculateScaledBoardHeight(),
        scaledBoardWidth: calculateScaledBoardWidth()
    }
})();