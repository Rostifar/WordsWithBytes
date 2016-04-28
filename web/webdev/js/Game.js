/**
 * Created by ross on 1/31/16.
 */
"use strict";
WordsWithBytes.Game = function(game){
    this.currentPlayer = null;
    this.players = [];
    this.boardImage = null;
    this.currentLetter = null;
    this.excessPixelsX = 0;
    this.excessPixelsY = 0;
    this.scaledBoardHeight = 0;
    this.scaledBoardWidth = 0;
    this.rackLocations = [];
    this.centerSquaresX = [];
    this.centerSquaresY = [];
    this.isPlayerGuessing = false;
    this.boardTileMap = null;
    this.scrabbleBoardLayer = null;
    this.scrabbleBoard = null;
    this.marker = null;
    this.cursors = null;
    this.scrabbleBoard = [];
    this.currentPlayer = null;
    this.playerRack = null;
};

/**
 * @WordsWithBytes.Game.Prototype
 * #purpose -> holds the main functionality for the whole scrabble game. Especially in the gui department.
 * #functionality -> many of the functions at the top of the scope are ones which setup the various calculations for running the game
 * smoothly and adding functionality to the board. While the functions towards the bottom are the general mechanics which helps decide
 * how the game is to operate(AKA. Main functionality)*/

WordsWithBytes.Game.prototype = {

    initScrabbleBoardTiles: function () {

        this.boardTileMap = game.add.tilemap("ScrabbleBoardTileSet");
        this.boardTileMap.addTilesetImage('ScrabbleBoardTilesetImage', 'tileImage'); //first arg needs to match the image "name" from the JSON file
        this.scrabbleBoardLayer = this.boardTileMap.createLayer('ScrabbleBoardLayer');
        this.scrabbleBoardLayer.resizeWorld();

        this.marker = game.add.graphics();
        this.marker.lineStyle(2, 0x000000, 1);
        this.marker.drawRect(0, 0, 40, 40);
    },

    getPlayerRack: function() {
        $.get("/GetLettersOnRack", function (data, status) {
             return JSON.parse(data);
        })
    },

    //Make an AJAX call using JQuery to get the JSON for the current state of the scrabble board and convent it to a Java script object
    getScrabbleBoard: function () {
        $.post("/GameInquiry", {inquiryType: "scrabbleBoard"}, function (data, status) {
            return JSON.parse(data);
        })
    },

    playMove: function() {

    },

    create: function () {
        this.initScrabbleBoardTiles();

        var controlButtonHeight = this.game.world.height + 60;
        var playWordButton = this.game.add.button(this.game.world.centerX, controlButtonHeight,'PlayWordButton');
        var passTurnButton = this.game.add.button(this.game.world.centerX + playWordButton.width + 5, controlButtonHeight, 'PassTurnButton');
        var swapWordsButton = this.game.add.button(this.game.world.centerX + (playWordButton.width + passTurnButton.width) + 10, controlButtonHeight, 'SwapWordsButton');
        var quitGameButton = this.game.add.button(this.game.world.width - playWordButton.width, controlButtonHeight, 'QuitGameButton');
        playWordButton.anchor.setTo(0.5, 0.5);
        passTurnButton.anchor.setTo(0.5, 0.5);
        swapWordsButton.anchor.setTo(0.5, 0.5);
        quitGameButton.anchor.setTo(0.5, 0.5);
        this.playerRack = this.getPlayerRack();
    },

    update: function () {
        this.marker.x = this.scrabbleBoardLayer.getTileX(game.input.activePointer.worldX) * 40;
        this.marker.y = this.scrabbleBoardLayer.getTileY(game.input.activePointer.worldY) * 40;
    },

    render: function() {
        game.debug.text('Location: ' + this.marker.x + "," + this.marker.y, 10, 10, '#efefef');
    }
};

