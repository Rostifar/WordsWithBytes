/**
 * Created by ross on 1/31/16.
 */
"use strict";
WordsWithBytes.Game = function(game){
    this.currentPlayer = null;
    this.players = [];
    this.scrabbleTileMap = null;
    this.scrabbleBoardLayer = null;
    this.scrabbleBoard = null;
    this.marker = null;
    this.scrabbleBoard = [];
    this.currentPlayer = null;
    this.playerRack = null;
    this.SQUARE_SIZE = 40;
    this.positionMap = {};
    this.currentWord = [];
    this.scrabbleBoardMap = [[]];
    this.exchangableLetters = null;
    this.currentBlankLetter = null;
    this.isFirstRound = true;
};


/**
 * @WordsWithBytes.Game.Prototype
 * #purpose -> holds the main functionality for the whole scrabble game. Especially in the gui department.
 * #functionality -> many of the functions at the top of the scope are ones which setup the various calculations for running the game
 * smoothly and adding functionality to the board. While the functions towards the bottom are the general mechanics which helps decide
 * how the game is to operate(AKA. Main functionality)*/

WordsWithBytes.Game.prototype = {

//adds ability to convert between canvas coordinates and scrabbleBoard positions
    populatePositionMap: function () {
        for (var i = 0; i < 15; i++) {
            this.positionMap[this.SQUARE_SIZE * i] = i;
        }
    },

    populateScrabbleBoard: function() {
      for(var i = 0; i < 15; i++) {
          this.scrabbleBoardMap[i] = [];
          for (var g = 0; g < 15; g++) {
              this.scrabbleBoardMap[i][g] = "noLetter";
          }
      }
    },

    setupBlankLetterExchangeSystem: function() {
        var letterKeys = ["A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"];
        var positionIndex = 0;
        var that = this;
        var letterIndex = 0;
        var background = this.game.add.sprite(160, 160, "square");
        var placementCoordinates = [200, 240, 280, 320, 360];
        this.exchangableLetters = this.game.add.group();
        this.exchangableLetters.add(background);

        function actionOnPress(sprite) {
            sprite.inputEnabled = true;
            sprite.events.onInputUp.add(function() {that.exchangeBlankLetter(sprite);}, this);
        }

        while (positionIndex < placementCoordinates.length) {
            var currentX = 200;
            for (var i = 0; currentX <= placementCoordinates[placementCoordinates.length - 1]; i++) {
                var sprite = this.game.add.sprite(currentX, placementCoordinates[positionIndex], letterKeys[letterIndex]);
                this.exchangableLetters.add(sprite);
                actionOnPress(sprite);
                currentX = placementCoordinates[i + 1];
                letterIndex++;
            }
            positionIndex++;
        }
        var zSprite = this.game.add.sprite(280, 400, "Z");
        actionOnPress(zSprite);
        this.exchangableLetters.add(zSprite);
        this.exchangableLetters.visible = false;
    },
//creates blank scrabbleBoard

    playWord: function() {
        var letterObjects = [];
        var wordOrientation = null;
        var that = this;
        var blankLetters = [this.currentWord.length];
        var strBlankLetters;

        function initLetterObjects() {
            for (var i = 0; i < that.currentWord.length; i++) {
                letterObjects[i] = {
                    letterName: that.currentWord[i].name,
                    columnLocation: that.currentWord[i].locationCol,
                    rowLocation: that.currentWord[i].locationRow
                };
            }
        }

        function findWordOrientation() {
            wordOrientation = (Math.abs(letterObjects[0].rowLocation - letterObjects[1].rowLocation) != 0 && (letterObjects.length > 1)) ? "vertical" : "horizontal";
            console.log(wordOrientation);
            for (var letter of letterObjects) {
                letter.positionToCheck = (wordOrientation == "horizontal") ? letter.columnLocation : letter.rowLocation;
                letter.constantPosition = (wordOrientation == "horizontal") ? letter.rowLocation : letter.columnLocation;
                letter.mapToCheck = (letterObject, board, add, subtract)=> {
                    if (add !== undefined) {
                        return wordOrientation == "horizontal" ? board[letterObject.positionToCheck + add][letterObject.constantPosition] :
                           board[letterObject.constantPosition][letterObject.positionToCheck + add];
                    }
                    if (subtract !== undefined) {
                        return wordOrientation == "horizontal" ? board[letterObject.positionToCheck - subtract][letterObject.constantPosition] :
                            board[letterObject.constantPosition][letterObject.positionToCheck - subtract];
                    }
                }
            }
        }

        function structurePlayedWord() {
            var sortedLetters = letterObjects.slice(0);
            console.log(sortedLetters);
            console.log(letterObjects);
            sortedLetters.sort(function (a, b) {
                return a.positionToCheck - b.positionToCheck;
            });
            console.log(sortedLetters);
            letterObjects = sortedLetters;
        }

        function getWordAsString() {
            var word = "";

            for (var letterObj of letterObjects) {
                word += letterObj.letterName;
            }
            return word;
        }

        function isValidWord() {

            function isWordConnectedToItself() {
                var lettersToCompare = letterObjects.slice(0);

                for (var letter of lettersToCompare) {
                    var letterIndex = lettersToCompare.indexOf(letter);
                    if (letter != lettersToCompare[lettersToCompare.length - 1]) {
                        letter.subtractedPosition = Math.abs(letter.positionToCheck - lettersToCompare[letterIndex + 1].positionToCheck);
                    } else {
                        letter.subtractedPosition = 0;
                    }
                }

                var invalidPositions = lettersToCompare
                    .filter((val) => {
                        return val.subtractedPosition > 1
                    });

                if (invalidPositions.length == 0) {
                    return true;
                } else {
                    for (var letterToCheck of invalidPositions) {
                        if (letterToCheck.mapToCheck(letterToCheck, that.scrabbleBoardMap, 1) !== "noLetter") {
                            return true;
                        }
                    }
                }
            }

            function isWordConnectedToLetterMass() {
                for (var letterObject of letterObjects) {
                    var objPositionToCheck = letterObject.positionToCheck;
                    var board = that.scrabbleBoardMap;

                    if (objPositionToCheck == 0 || objPositionToCheck == 14) {
                        if (letterObject.positionToCheck == 0 && letterObject.mapToCheck(letterObject, board, 1) !== "noLetter") {
                            return true;
                        } else if (letterObject.positionToCheck == 14 && letterObject.mapToCheck(letterObject, board, undefined, 1) !== "noLetter") {
                            return true;
                        }
                    } else if (letterObject.mapToCheck(letterObject, board, 1) !== "noLetter" || letterObject.mapToCheck(letterObject, board, undefined, 1) !== "noLetter" ) {
                        return true;
                    }
                }
                return false;
            }
            if (!isWordConnectedToItself() || !isWordConnectedToLetterMass()) {
                alert("your letter placement in invalid, please try again");
            }
        }

        function convertBlankLetters() {
            for (var i = 0; i < that.currentWord.length; i++) {
                if (that.currentWord[i].isBlankLetter) {
                    blankLetters[i] = that.currentWord[i].key;
                } else {
                    blankLetters[i] = "#";
                }
            }
            strBlankLetters = blankLetters.join("");
        }

        initLetterObjects();
        findWordOrientation();
        structurePlayedWord();
        isValidWord();
        convertBlankLetters();

        //TODO: if Word checks out, servlet return true & add letters to front end board
        $.post("/PlayWord",
            {"wordPlayed":getWordAsString(), "letterPositionsCol": letterObjects[0].columnLocation, "letterPositionsRow": letterObjects[0].rowLocation, "wordOrientation": wordOrientation, "blankLetters": strBlankLetters},
            function(data, status){});
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

    initScrabbleBoardTiles: function () {

        this.scrabbleTileMap = this.game.add.tilemap("ScrabbleBoardTileSet");
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

        function isSpaceUsed() {
            var xPos = that.marker.x;
            var yPos = that.marker.y;
            return (that.scrabbleBoardMap[that.positionMap[xPos]][that.positionMap[yPos]] = !"noLetter");
        }

        function setUpLetter() {
            if (that.marker.y < 600 && !isSpaceUsed()) {
                letterImage.x = that.marker.x;
                letterImage.y = that.marker.y;
                letterImage.locationCol = that.positionMap[letterImage.x];
                letterImage.locationRow = that.positionMap[letterImage.y];
                that.currentWord.push(letterImage);

                if (letterImage.isBlankLetter == true) {
                    that.exchangableLetters.visible = true;
                    letterImage.visible = false;
                    that.currentBlankLetter = letterImage;
                }
            } else {
                letterImage.x = letterImage.originalPosition.x;
                letterImage.y = letterImage.originalPosition.y;
                letterImage.locationCol = null;
                letterImage.locationRow = null;

                if ((letterImage.name != letterImage.key) && (letterImage.isBlankLetter)) {
                    letterImage.loadTexture("_");
                }
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
            sprite.isBlankLetter = (sprite.name == "_");
            sprite.events.onInputUp.add(function(sprite) {this.placeLetterOnBoard(sprite);}, this);
        }
    },

    exchangeBlankLetter: function(sprite) {
        this.currentBlankLetter.loadTexture(sprite.key);
        this.currentBlankLetter.visible = true;
        this.exchangableLetters.visible = false;
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

    create: function () {
        this.populatePositionMap();
        this.populateScrabbleBoard();
        this.initScrabbleBoardTiles();
        this.initButtons();

        var promise = $.ajax("/GetLettersOnRack");
        promise.done(this.getRackSuccess.bind(this));
        this.setupBlankLetterExchangeSystem();

        this.marker = this.game.add.graphics();
        this.marker.lineStyle(2, 0x000000, 1);
        this.marker.drawRect(0, 0, this.SQUARE_SIZE, this.SQUARE_SIZE);
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

