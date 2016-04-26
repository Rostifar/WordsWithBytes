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
};

/**
 * @WordsWithBytes.Game.Prototype
 * #purpose -> holds the main functionality for the whole scrabble game. Especially in the gui department.
 * #functionality -> many of the functions at the top of the scope are ones which setup the various calculations for running the game
 * smoothly and adding functionality to the board. While the functions towards the bottom are the general mechanics which helps decide
 * how the game is to operate(AKA. Main functionality)*/

WordsWithBytes.Game.prototype = {

    setupScrabbleBoard: function() {
        while(this.scrabbleBoard.push([]) < 15);
    },

    setupScrabbleGameDimensions: function () {
        this.excessPixelsX = (this.game.width - this.boardImage.width) / 2;
        this.excessPixelsY = (this.game.height - this.boardImage.height) / 2;
        this.scaledBoardHeight = this.game.height - this.excessPixelsY;
        this.scaledBoardWidth = this.game.width - this.excessPixelsX;
    },

    setupRackLocations: function () {
        var counter1 = 0;
        var counter2 = 0;

        while (counter1 < 3) {
            ++counter1;
            this.rackLocations.push(1 + (50 * counter1));
        }
        while (counter2 < 3) {
            ++counter2;
            this.rackLocations.push(1 - (50 * counter2));
        }
    },

    sortNumber: function (a, b) {
        return a - b;
    },

    createLetter: function (imageKey) {
        var letter = this.game.add.sprite(this.game.width - 40, yLocation, imageKey);
        letter.positionOnRack = xLocation;
        letter.anchor.setTo(0.5);
        letter.inputEnabled = true;
        letter.input.enableDrag(true);
        letter.events.onDragStart.add(function () {
            this.isPlayerGuessing = true;
            this.currentLetter = letter;
        }, this);
        letter.events.onInputUp.add(function () {
            this.isPlayerGuessing = false;
            this.currentLetter = null;
        }, this);
        gameRack.push(letter);
    },

    calculateCenterSquares: function () {
        var xScaleFactor = this.scaledBoardWidth / 15;
        var yScaleFactor = this.scaledBoardHeight / 15;

        for (var initialPixelWidth = this.excessPixelsX; initialPixelWidth <= this.scaledBoardWidth; initialPixelWidth += xScaleFactor) {
            this.centerSquaresX.push(initialPixelWidth);
        }
        for (var initialPixelHeight = this.excessPixelsY; initialPixelHeight <= this.scaledBoardHeight; initialPixelHeight += yScaleFactor) {
            this.centerSquaresY.push(initialPixelHeight);
        }
    },

    isPlayInBoardProximity: function () {
        var minimumWidth = this.excessPixelsX;
        var minimumHeight = this.excessPixelsY;
        var maximumHeight = this.scaledBoardHeight;
        var maximumWidth = this.scaledBoardWidth;

        return ((currentLetter.x >= minimumWidth && currentLetter.x <= maximumWidth) && (currentLetter.y >= minimumHeight && currentLetter.y <= maximumHeight));
    },

    searchForClosestSquare: function () {
        var closestSquaresX = [];
        var closestSquaresY = [];

        for (var i = 0; i < 15; i++) {
            closestSquaresX.push(Math.abs(this.currentLetter.x - this.centerSquaresX[i]));
            closestSquaresY.push(Math.abs(this.currentLetter.y - this.centerSquaresY[i]));
        }

        closestSquaresX.sort(this.sortNumber);
        closestSquaresY.sort(this.sortNumber);
        currentLetter.x = closestSquaresX[0];
        currentLetter.y = closestSquaresY[0];
        closestSquaresX.length = 0;
        closestSquaresY.length = 0;
    },

    initMechanicsCalculations: function () {
        this.setupScrabbleGameDimensions();
        this.setupRackLocations();
        this.calculateCenterSquares();
    },

    calculationsTestSuite: function () {
        console.log(this.excessPixelsX, this.excessPixelsY, this.scaledBoardWidth, this.scaledBoardHeight);

        for (var i = 0; i < this.centerSquaresX.length; i++) {
            console.log(this.centerSquaresX[i]);
            console.log(this.centerSquaresY[i])
        }
    },

    initScrabbleBoardTiles: function () {

        this.boardTileMap = game.add.tilemap("ScrabbleBoardTileSet");
        this.boardTileMap.addTilesetImage('ScrabbleBoardTilesetImage', 'tileImage'); //first arg needs to match the image "name" from the JSON file
        this.scrabbleBoardLayer = this.boardTileMap.createLayer('ScrabbleBoardLayer');
        this.scrabbleBoardLayer.resizeWorld();

        this.marker = game.add.graphics();
        this.marker.lineStyle(2, 0x000000, 1);
        this.marker.drawRect(0, 0, 40, 40);
    },

    //Make an AJAX call using JQuery to get the JSON for the current state of the scrabble board and convent it to a Java script object
    getScrabbleBoard: function () {
        $.post("/GameInquiry", {inquiryType: "scrabbleBoard"}, function (data, status) {
            var gameObj = JSON.parse(data);
            console.log(gameObj);
            this.scrabbleBoard = gameObj;
        })
    },

    playMove: function() {

    },

    create: function () {
        this.initScrabbleBoardTiles();
        this.setupScrabbleBoard();

        var controlButtonHeight = this.game.world.height + 60;
        var playWordButton = this.game.add.button(this.game.world.centerX, controlButtonHeight,'PlayWordButton');
        var passTurnButton = this.game.add.button(this.game.world.centerX + playWordButton.width + 5, controlButtonHeight, 'PassTurnButton');
        var swapWordsButton = this.game.add.button(this.game.world.centerX + (playWordButton.width + passTurnButton.width) + 10, controlButtonHeight, 'SwapWordsButton');
        var quitGameButton = this.game.add.button(this.game.world.width - playWordButton.width, controlButtonHeight, 'QuitGameButton');
        playWordButton.anchor.setTo(0.5, 0.5);
        passTurnButton.anchor.setTo(0.5, 0.5);
        swapWordsButton.anchor.setTo(0.5, 0.5);
        quitGameButton.anchor.setTo(0.5, 0.5);
    },

    update: function () {
        this.marker.x = this.scrabbleBoardLayer.getTileX(game.input.activePointer.worldX) * 40;
        this.marker.y = this.scrabbleBoardLayer.getTileY(game.input.activePointer.worldY) * 40;

    },

    render: function() {
        game.debug.text('Location: ' + this.marker.x + "," + this.marker.y, 10, 10, '#efefef');
    //  game.debug.text('Left-click to paint. Shift + Left-click to select tile. Arrows to scroll.', 40, 40, '#efefef');
    }
};

