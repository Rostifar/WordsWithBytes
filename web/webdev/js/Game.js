/**
 * Created by ross on 1/31/16.
 */
"use strict";
WordsWithBytes.Game = function(game){
    this.currentPlayer = null;
    this.players = [];
    this.isPlayerGuessing = false;
    this.scrabbleTileMap = null;
    this.scrabbleBoardLayer = null;
    this.scrabbleBoard = null;
    this.marker = null;
    this.cursors = null;
    this.scrabbleBoard = [];
    this.currentPlayer = null;
    this.playerRack = null;
    this.racklayer = null;
    this.SQUARE_SIZE = 40;
    this.positionMap = {};
    this.currentWord = [];
    this.wordOrientation = null;
};


/**
 * @WordsWithBytes.Game.Prototype
 * #purpose -> holds the main functionality for the whole scrabble game. Especially in the gui department.
 * #functionality -> many of the functions at the top of the scope are ones which setup the various calculations for running the game
 * smoothly and adding functionality to the board. While the functions towards the bottom are the general mechanics which helps decide
 * how the game is to operate(AKA. Main functionality)*/

WordsWithBytes.Game.prototype = {

    populatePositionMap: function () {
        for (var i = 0; i < 15; i++) {
            this.positionMap[this.SQUARE_SIZE * i] = i;
        }
    },

    initButtons: function() {
        var controlButtonHeight = this.game.world.height + 60;
        var playWordButton = this.game.add.button(440, 640,'PlayWordButton', function() {
            var letters = [];
            var positionsCol = [];
            var positionsRow = [];

            for (var i = 0; i < this.currentWord.length; i++) {
                letters[i] = this.currentWord[i].name;
                positionsCol[i] = this.currentWord[i].locationCol;
                positionsRow[i] = this.currentWord[i].locationRow;
            }

            //Checks to make sure NullPointerException isn't called[only one letter being called; while trying to check orientation with two]
            if (letters.length > 1) {
                this.determineWordOrientation(positionsRow[0], positionsRow[1]);
                if (this.wordOrientation == "horizontal") {
                    letters = this.structurePlayedWord(letters, positionsCol);
                } else {
                    letters = this.structurePlayedWord(letters, positionsRow);
                }
            } else {
                this.wordOrientation = "horizontal";
            }

            $.post("PlayWord", {"wordPlayed": letters, "letterPositionsCol": positionsCol, "letterPositionsRow": positionsRow, "wordOrientation": this.wordOrientation}, function(data, status) {
            });
        }, this, 2, 1, 0);
        var passTurnButton = this.game.add.button(this.game.world.centerX + playWordButton.width + 5, controlButtonHeight, 'PassTurnButton');
        var swapWordsButton = this.game.add.button(this.game.world.centerX + (playWordButton.width + passTurnButton.width) + 10, controlButtonHeight, 'SwapWordsButton');
        var quitGameButton = this.game.add.button(this.game.world.width - playWordButton.width, controlButtonHeight, 'QuitGameButton');
        playWordButton.anchor.setTo(0.5, 0.5);
        passTurnButton.anchor.setTo(0.5, 0.5);
        swapWordsButton.anchor.setTo(0.5, 0.5);
        quitGameButton.anchor.setTo(0.5, 0.5);

    },

//ensures that the letter is correctly interpreted so that the players order of placement can be neglected
    structurePlayedWord: function(letters, valuesToCompare) {
        var sortedLetters = [];
        for(var i = 0; i < letters.length; i++) {
            valuesToCompare[i].originalPosition = i;
        }
        valuesToCompare.sort(function(a, b) {
            return a - b;
        });

        for (var k = 0; k < letters.length; k++) {
            sortedLetters[k] = letters[valuesToCompare[k].originalPosition];
        }
        return sortedLetters;
    },


    initScrabbleBoardTiles: function () {

        this.scrabbleTileMap = game.add.tilemap("ScrabbleBoardTileSet");
        this.scrabbleTileMap.addTilesetImage('ScrabbleBoardTileset', 'tileImage'); //first arg needs to match the image "name" from the JSON file
        this.scrabbleTileMap.addTilesetImage('ScrabbleAlphabetTileset', 'alphabetImage'); //first arg needs to match the image "name" from the JSON file
        //this.boardTileMap.addTilesetImage('ScrabbleAlphabetTilesetImage', 'alphabetImage'); //first arg needs to match the image "name" from the JSON file
        this.scrabbleBoardLayer = this.scrabbleTileMap.createLayer('ScrabbleBoardLayer');

        this.scrabbleBoardLayer.resizeWorld();
        //this.scrabbleBoardLayer.debug = true;
        var boardImageTileMap = this.scrabbleTileMap.tilesets[this.scrabbleTileMap.getTilesetIndex('ScrabbleBoardTilesetImage')];
        var currentTile = this.scrabbleTileMap.getTile(this.scrabbleBoardLayer.getTileX(1) * this.SQUARE_SIZE, this.scrabbleBoardLayer.getTileY(1) * this.SQUARE_SIZE);
    },
    
    initScrabbleRack: function () {
        
        console.log(this.playerRack);

        var lettersGroup = this.game.add.group();

        //for each tile to be on the rack find the correct image based on the populated rack from the backend
        for (var rackCol = 0; rackCol < 7; rackCol++) {
            var letterToPlace = this.playerRack[rackCol];
            
            var tile = this.scrabbleTileMap.getTile(rackCol, 17);
            var sprite = this.game.add.sprite(rackCol * 41, 640, letterToPlace);
            sprite.originalPosition = {
                x: sprite.x,
                y: sprite.y
            };
            sprite.inputEnabled = true;
            sprite.input.enableDrag(true);
            sprite.name = letterToPlace;
            sprite.events.onInputUp.add(function(sprite) {
                if (this.marker.y < 600) {
                    sprite.x = this.marker.x;
                    sprite.y = this.marker.y;
                    sprite.locationCol = this.positionMap[sprite.x];
                    sprite.locationRow = this.positionMap[sprite.y];
                    this.currentWord.push(sprite);
                } else {
                    sprite.x = sprite.originalPosition.x;
                    sprite.y = sprite.originalPosition.y;
                    sprite.locationCol = null;
                    sprite.locationRow = null;
                }
            }, this);
        }
    },

    //Make an AJAX call using JQuery to get the JSON for the current state of the scrabble board and convent it to a Java script object
    getScrabbleBoard: function () {
        var board = null;
        $.post("/GameInquiry", {inquiryType: "scrabbleBoard"}, function (data, status) {
            board = JSON.parse(data);
        })
    },

    determineWordOrientation: function(row1, row2) {

        if ((Math.abs(row1 - row2) == !0)) {
            this.wordOrientation = "vertical";
        } else {
            this.wordOrientation = "horizontal";
        }
    },

    successFunc: function(data) {
        console.log(this);
        console.log(data);
        this.playerRack = JSON.parse(data);
        this.initScrabbleRack();
    },


    failureFunc: function() {
        console.log("Call to GetPlayRack failed");
    },

    create: function () {
        this.populatePositionMap();
        this.initScrabbleBoardTiles();
        this.initButtons();
        var rack = null;
        var promise = $.ajax("/GetLettersOnRack");
        promise.done(this.successFunc.bind(this));

        this.marker = game.add.graphics();
        this.marker.lineStyle(2, 0x000000, 1);
        this.marker.drawRect(0, 0, this.SQUARE_SIZE, this.SQUARE_SIZE);
        this.cursors = game.input.keyboard.createCursorKeys();
    },

    update: function () {
        if (this.scrabbleBoardLayer != null) {
            this.marker.x = this.scrabbleBoardLayer.getTileX(game.input.activePointer.worldX) * this.SQUARE_SIZE;
            this.marker.y = this.scrabbleBoardLayer.getTileY(game.input.activePointer.worldY) * this.SQUARE_SIZE;
        }

    },

    render: function() {
        if (this.scrabbleBoardLayer != null) {
            game.debug.text('Location: ' + this.marker.x + "," + this.marker.y, 10, 10, '#efefef');
        }
    //  game.debug.text('Left-click to paint. Shift + Left-click to select tile. Arrows to scroll.', this.SQUARE_SIZE, this.SQUARE_SIZE, '#efefef');
    }
};

