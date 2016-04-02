/**
 * Created by ross on 1/31/16.
 */
"use strict";
WordsWithBytes.Game = function(game){
    this.game = game;
    this.currentPlayer = null;
    this.boardImage = null;
    this.currentLetter = null;
    WordsWithBytes.scrabbleBoard.excessPixelsX = 0;
    WordsWithBytes.scrabbleBoard.excessPixelsY = 0;
    WordsWithBytes.scrabbleBoard.scaledBoardHeight = 0;
    WordsWithBytes.scrabbleBoard.scaledBoardWidth = 0;
    WordsWithBytes.rack.locations = [];
    WordsWithBytes.interfaceMechanics.centerSquaresX = [];
    WordsWithBytes.interfaceMechanics.centerSquaresY = [];
    WordsWithBytes.rack.numberOfPlayers = 0;

};

WordsWithBytes.Game.prototype =  {

    create: function() {
        this.game.add.sprite(0, 0, 'space-background');
        this.boardImage = this.game.add.sprite(this.game.world.centerX, this.game.world.centerY, 'scrabbleBoard');
        this.boardImage.anchor.setTo(0.5);
        var scrabbleBoard = WordsWithBytes.Game.ScrabbleBoard;
    }
};

WordsWithBytes.scrabbleBoard = {

    caluculateExcessPixelsX: function() {
        WordsWithBytes.scrabbleBoard.excessPixelsX = (this.game.width - this.boardImage.width) / 2;
    },
    calculateExcessPixelsY: function () {
        WordsWithBytes.scrabbleBoard.excessPixelsY = (this.game.world.height - this.scrabbleBoardImage.height) / 2;
    },
    calculateScaledBoardHeight: function () {
        WordsWithBytes.scrabbleBoard.scaledBoardHeight = this.game.height - WordsWithBytes.scrabbleBoard.excessPixelsY;
    },
    calculateScaledBoardWidth: function () {
        WordsWithBytes.scrabbleBoard.scaledBoardWidth = this.game.width - WordsWithBytes.scrabbleBoard.excessPixelsX;
    }
};

WordsWithBytes.interfaceMechanics = {

    sortNumber: function(a, b) {
      return a - b;
    },

    isInBoardProximity: function() {
        var minimumWidth = WordsWithBytes.scrabbleBoard.excessPixelsX;
        var minimumHeight = WordsWithBytes.scrabbleBoard.excessPixelsY;
        var maximumHeight = WordsWithBytes.scrabbleBoard.scaledBoardHeight;
        var maximumWidth = WordsWithBytes.scrabbleBoard.scaledBoardWidth;

        return ((currentLetter.x >= minimumWidth && currentLetter.x <= maximumWidth) && (currentLetter.y >= minimumHeight && currentLetter.y <= maximumHeight));
    },

    calculateCenterSquare: function() {
        var xScaleFactor = WordsWithBytes.scrabbleBoard.scaledBoardWidth / 15;
        var yScaleFactor = WordsWithBytes.scrabbleBoard.scaledBoardHeight / 15;
        var excessPixelsX = WordsWithBytes.scrabbleBoard.excessPixelsX;
        var excessPixelsY = WordsWithBytes.scrabbleBoard.excessPixelsY;
        var scrabbleBoardHeight = WordsWithBytes.scrabbleBoard.scaledBoardHeight;
        var scrabbleBoardWidth = WordsWithBytes.scrabbleBoard.scaledBoardWidth;

        for (var initialPixelWidth = excessPixelsX; initialPixelWidth <= scrabbleBoardWidth; initialPixelWidth += xScaleFactor) {
            WordsWithBytes.interfaceMechanics.centerSquaresX.push(initialPixelWidth);
        }

        for (var initialPixelHeight = excessPixelsY; initialPixelHeight <= scrabbleBoardHeight; initialPixelHeight += yScaleFactor) {
            WordsWithBytes.interfaceMechanics.centerSquaresY.push(initialPixelHeight);
        }
    },

    searchForClosestSquare: function() {
        var closestSquaresX = [];
        var closestSquaresY = [];

        for (var i = 0; i < 15; i++) {
            closestSquaresX.push(Math.abs(this.currentLetter.x - WordsWithBytes.interfaceMechanics.centerSquaresX[i]));
            closestSquaresY.push(Math.abs(this.currentLetter.y - WordsWithBytes.interfaceMechanics.centerSquaresY[i]));
        }
        closestSquaresX.sort(this.sortNumber);
        closestSquaresY.sort(this.sortNumber);
        currentLetter.x = closestSquaresX[0];
        currentLetter.y = closestSquaresY[0];
        }

};

WordsWithBytes.rack = {

    analyzeLetterPlacement: function() {},

    calculateLocations: function() {
        var counter1 = 0;
        var counter2 = 0;

        while (counter1 < 3) {
            ++counter1;
            WordsWithBytes.rack.locations.push(1 + (50 * counter1));
        }
        while (counter2 < 3) {
            ++counter2;
            WordsWithBytes.rack.locations.push(1 - (50 * counter2));
        }
    }
};
