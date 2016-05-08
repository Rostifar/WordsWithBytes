/**
 * Created by ross on 1/31/16.
 */
"use strict";
WordsWithBytes.Game = function(game){
    this.currentPlayer = null;
    this.players = [];
    this.currentLetter = null;
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
};


/**
 * @WordsWithBytes.Game.Prototype
 * #purpose -> holds the main functionality for the whole scrabble game. Especially in the gui department.
 * #functionality -> many of the functions at the top of the scope are ones which setup the various calculations for running the game
 * smoothly and adding functionality to the board. While the functions towards the bottom are the general mechanics which helps decide
 * how the game is to operate(AKA. Main functionality)*/

WordsWithBytes.Game.prototype = {

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


        function selectedTile(sprite) {
            sprite.x = this.marker.x;
            sprite.y = this.marker.y;
        }
        console.log(this.playerRack);
        var tile;

        var lettersGroup = this.game.add.group();

        //for each tile to be on the rack find the correct image based on the populated rack from the backend
        for (var rackCol = 0; rackCol < 7; rackCol++) {
            var letterToPlace = this.playerRack[rackCol];

            var tile = this.scrabbleTileMap.getTile(rackCol, 17);
            var sprite = this.game.add.sprite(rackCol * 41, 640, letterToPlace);
            sprite.inputEnabled = true;
            sprite.input.enableDrag(true);
            sprite.name = letterToPlace;
            sprite.events.onInputUp.add(selectedTile, this);
        }
    },


    //Make an AJAX call using JQuery to get the JSON for the current state of the scrabble board and convent it to a Java script object
    getScrabbleBoard: function () {
        var board = null;
        $.post("/GameInquiry", {inquiryType: "scrabbleBoard"}, function (data, status) {
            board =  JSON.parse(data);
        })
    },

    //Plays the current players move when player decides they are ready
    playMove: function() {

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
        this.initScrabbleBoardTiles();
        var rack = null;
        var promise = $.ajax("/GetLettersOnRack");
        promise.done(this.successFunc.bind(this));

        var controlButtonHeight = this.game.world.height + 60;
        var playWordButton = this.game.add.button(this.game.world.centerX, controlButtonHeight,'PlayWordButton');
        var passTurnButton = this.game.add.button(this.game.world.centerX + playWordButton.width + 5, controlButtonHeight, 'PassTurnButton');
        var swapWordsButton = this.game.add.button(this.game.world.centerX + (playWordButton.width + passTurnButton.width) + 10, controlButtonHeight, 'SwapWordsButton');
        var quitGameButton = this.game.add.button(this.game.world.width - playWordButton.width, controlButtonHeight, 'QuitGameButton');
        playWordButton.anchor.setTo(0.5, 0.5);
        passTurnButton.anchor.setTo(0.5, 0.5);
        swapWordsButton.anchor.setTo(0.5, 0.5);
        quitGameButton.anchor.setTo(0.5, 0.5);

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

