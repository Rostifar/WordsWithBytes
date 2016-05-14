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
    this.wordOrientation = null;
    this.marker = null;
    this.cursors = null;
    this.scrabbleBoard = [];
    this.currentPlayer = null;
    this.playerRack = null;
    this.racklayer = null;
    this.SQUARE_SIZE = 40;
    this.positionMap = {};
    this.currentWord = [];
    this.scrabbleBoardMap = [[]];
};


/**
 * @WordsWithBytes.Game.Prototype
 * #purpose -> holds the main functionality for the whole scrabble game. Especially in the gui department.
 * #functionality -> many of the functions at the top of the scope are ones which setup the various calculations for running the game
 * smoothly and adding functionality to the board. While the functions towards the bottom are the general mechanics which helps decide
 * how the game is to operate(AKA. Main functionality)*/

WordsWithBytes.Game.prototype = {

    create: function () {
        this.populatePositionMap();
        this.initScrabbleBoardTiles();
        this.initButtons();
        this.populateScrabbleBoard();
        var rack = null;
        var promise = $.ajax("/GetLettersOnRack");
        promise.done(this.getRackSuccess.bind(this));

        this.marker = game.add.graphics();
        this.marker.lineStyle(2, 0x000000, 1);
        this.marker.drawRect(0, 0, this.SQUARE_SIZE, this.SQUARE_SIZE);
        this.cursors = game.input.keyboard.createCursorKeys();
    },

//adds ability to convert between canvas coordinates and scrabbleBoard positions
    populatePositionMap: function () {
        for (var i = 0; i < 15; i++) {
            this.positionMap[this.SQUARE_SIZE * i] = i;
        }
    },
//creates blank scrabbleBoard
    populateScrabbleBoard: function() {
        for(var f = 0; f < 14; f++) {
            for(var y = 0; y < 14; y++) {
                this.scrabbleBoardMap[[f, y]] = "noLetter";
            }
        }
    },

    playWord: function() {
        var letterObjects = [];
        var wordOrientation = null;
        var that = this;

        function initLetterObjects() {
            for (var i = 0; i < that.currentWord.length; i++) {
                letterObjects = [{
                    letterName: that.currentWord[i].name,
                    columnLocation: that.currentWord[i].locationCol,
                    rowLocation: that.currentWord[i].locationRow
                }];
            }
        }

        function exchangeBlankLetter() {
            for (var letterObj of letterObjects) {
                if (letterObj.letterName == "_") {
                    //exchangeLetterServlet
                }
            }
        }
        function findWordOrientation() {
            wordOrientation = (Math.abs(letterObjects[0].rowLocation - letterObjects[0].rowLocation) == !0) ? "vertical" : "horizontal";
        }
        function structurePlayedWord() {
            var sortedLettersCol = letterObjects.slice(0);
            sortedLettersCol.sort(function (a, b) {
                return a.columnLocation - b.columnLocation;
            });
            var sortedLettersRow = letterObjects.splice(0);
            sortedLettersRow.sort(function(a, b) {
                return a.rowLocation - b.rowLocation;
            });
            letterObjects = (wordOrientation == "horizontal") ? sortedLettersCol : sortedLettersRow;
        }
        initLetterObjects();
        exchangeBlankLetter();
        findWordOrientation();
        structurePlayedWord();
        console.log(this);
        $.post("/PlayWord", {"wordPlayed": 1, "letterPositionsCol": letterObjects[0].columnLocation, "letterPositionsRow": letterObjects[0].rowLocation, "wordOrientation": wordOrientation}, function(data, status){});
    },

    skipTurn: function() {},

    initButtons: function() {
        var controlButtonHeight = this.game.world.height + 60;
        var that = this;
        var playWordButton = this.game.add.button(440, 640,'PlayWordButton', function() {that.playWord();}, this, 2, 1, 0);
        var passTurnButton = this.game.add.button(this.game.world.centerX + playWordButton.width + 5, controlButtonHeight, 'PassTurnButton');
        var swapWordsButton = this.game.add.button(this.game.world.centerX + (playWordButton.width + passTurnButton.width) + 10, controlButtonHeight, 'SwapWordsButton');
        var quitGameButton = this.game.add.button(this.game.world.width - playWordButton.width, controlButtonHeight, 'QuitGameButton');
        playWordButton.anchor.setTo(0.5, 0.5);
        passTurnButton.anchor.setTo(0.5, 0.5);
        swapWordsButton.anchor.setTo(0.5, 0.5);
        quitGameButton.anchor.setTo(0.5, 0.5);

    },

    structurePlayedWord: function(lettersObjects) {

    },

    initScrabbleBoardTiles: function () {

        this.scrabbleTileMap = game.add.tilemap("ScrabbleBoardTileSet");
        this.scrabbleTileMap.addTilesetImage('ScrabbleBoardTileset', 'tileImage'); //first arg needs to match the image "name" from the JSON file
        this.scrabbleTileMap.addTilesetImage('ScrabbleAlphabetTileset', 'alphabetImage'); //first arg needs to match the image "name" from the JSON file
        this.scrabbleBoardLayer = this.scrabbleTileMap.createLayer('ScrabbleBoardLayer');
        this.scrabbleBoardLayer.resizeWorld();
        var boardImageTileMap = this.scrabbleTileMap.tilesets[this.scrabbleTileMap.getTilesetIndex('ScrabbleBoardTilesetImage')];
        var currentTile = this.scrabbleTileMap.getTile(this.scrabbleBoardLayer.getTileX(1) * this.SQUARE_SIZE, this.scrabbleBoardLayer.getTileY(1) * this.SQUARE_SIZE);
    },

    validateWordPosition: function(letters, col, row) {

    },

    placeLetterOnBoard: function(sprite) {
        var letterImage = sprite;
        var that = this;

        function setUpLetter() {
            if(that.marker.y < 600) {
                letterImage.x = that.marker.x;
                letterImage.y = that.marker.y;
                letterImage.locationCol = that.positionMap[letterImage.x];
                letterImage.locationRow = that.positionMap[letterImage.y];
                that.currentWord.push(letterImage)
            } else {
                letterImage.x = letterImage.originalPosition.x;
                letterImage.y = letterImage.originalPosition.y;
                letterImage.locationCol = null;
                letterImage.locationRow = null;
            }
        }
        setUpLetter();
    },

    initScrabbleRack: function () {
        console.log(this.playerRack);
        var lettersGroup = this.game.add.group();
        var that = this;

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
            sprite.events.onInputUp.add(function(sprite) {this.placeLetterOnBoard(sprite);}, this);
        }
    },

    //Make an AJAX call using JQuery to get the JSON for the current state of the scrabble board and convent it to a Java script object
    getScrabbleBoard: function () {
        var board = null;
        $.post("/GameInquiry", {inquiryType: "scrabbleBoard"}, function (data, status) {
            board = JSON.parse(data);
        })
    },

    getRackSuccess: function(data) {
        console.log(this);
        console.log(data);
        this.playerRack = JSON.parse(data);
        this.initScrabbleRack();
    },


    getRackFailure: function() {
        console.log("Call to GetPlayRack failed");
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
    }
};

