/**
 * Created by ross on 1/31/16.
 */
"use strict";
WordsWithBytes.Game = function(game){};

var Game = WordsWithBytes.Game;

Game.constructs = {

    ScrabbleBoard: function(scrabbleBoardImage, scrabbleGameWidth, scrabbleGameHeight) {
        this.scrabbleBoardImage = scrabbleBoardImage;
        this.gameWidth = scrabbleGameWidth;
        this.gameHeight = scrabbleGameHeight;
    },
    Rack: function(numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }
};

Game.constructs.ScrabbleBoard.prototype = {
    excessPixelsX: this.gameWidth,
    excessPixelsY: function() {
        return (this.game.height - this.scrabbleBoardImage.height) / 2;
    },
    scaledBoardHeight: function() {
        return this.game.height - ((this.game.height - this.scrabbleBoardImage.height) / 2);
    },
    scaledBoardWidth: function() {
        return this.game.width - ((this.game.width - this.scrabbleBoardImage.width) / 2);
    },
    locations: function() {
        var locations = [];
        var counter1 = 0;
        var counter2 = 0;

        while (counter1 < 3) {
            ++counter1;
            locations.push(this.game.centerX + (50 * counter1));
        }
        while (counter2 < 3) {
            ++counter2;
            locations.push(this.game.centerY - (50 * counter2));
        }
        return locations;
    }
};

Game.constructs.Rack.prototype = {

    createRackLocations: function() {}
};



Game.prototype = {

        create: function () {
            this.game.add.sprite(0, 0, 'space-background');
            var boardImage = this.game.add.sprite(this.game.world.centerX, this.game.world.centerY, 'scrabbleBoard');
            boardImage.anchor.setTo(0.5);
            var scrabbleBoard = new Game.constructs.ScrabbleBoard(boardImage, this.game.width, this.game.height);
            console.log(scrabbleBoard.locations);
            //var rack = new Game.constructs.Rack(getNumberOfPlayers); //Backend implementation
           // rack.getLetterToAdd('bl');
           // rack.getLetterToAdd('a');
        },

        update: function () {
        }
};

