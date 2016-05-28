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
    this.lettersOnRack = [];
    this.scrabbleBoardMap = [[]];
    this.exchangableLetters = null;
    this.currentBlankLetter = null;
    this.isFirstRound = true;
    this.dropLetter = null;
    this.letterKeys = ["A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"];
    this.isExchangingLetters = false;

};


/**
 * @WordsWithBytes.Game.Prototype
 * #purpose -> holds the main functionality for the whole scrabble game. Especially in the gui department.
 * #functionality -> many of the functions at the top of the scope are ones which setup the various calculations for running the game
 * smoothly and adding functionality to the board. While the functions towards the bottom are the general mechanics which helps decide
 * how the game is to operate(AKA. Main functionality)*/

WordsWithBytes.Game.prototype = {

/**
 * @populatePositionMap
 * -populates array to convert between between game canvas coordinates to ScrabbleBoard positions
 * -Example = convert x[640], y[320] -> [12][4]
 * */
    populatePositionMap: function () {
        for (var i = 0; i < 15; i++) {
            this.positionMap[this.SQUARE_SIZE * i] = i;
        }
    },

    /**
     * @populateScrabbleBoard
     * #purpose -> sets up a blank scrabbleBoard.
     * Allows the frontend to check whether a played word is valid or not
     * */
    populateScrabbleBoard: function() {
      for(var i = 0; i < 15; i++) {
          this.scrabbleBoardMap[i] = [];
          for (var g = 0; g < 15; g++) {
              this.scrabbleBoardMap[i][g] = "noLetter";
          }
      }
    },

    /**
     * @isSpaceTaken
     * param-> col = user selected column on ScrabbleBoard; row = user selected row on ScrabbleBoard
     * purpose-> enables quick functionality for checking whether a selected position already has been used
     * */
    isSpaceTaken: function(col, row) {
        return this.scrabbleBoardMap[col][row] !== "noLetter";
    },

    /**
     * @setupBlankLetterExchangeSystem
     * purpose-> creates animations and functionality which aid in exchanging blank letters for real letters.
     * Used enacted when a player puts a blank tile on the ScrabbleBoard
     * */
    setupBlankLetterExchangeSystem: function() {
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
                var sprite = this.game.add.sprite(currentX, placementCoordinates[positionIndex], this.letterKeys[letterIndex]);
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

    /**
     * @playWord
     * purpose-> setup the system for playing a word.
     * subset ->
     *  @initLetterObjects
     *  purpose-> creates letter objects from sprites in order to manage sprite locations and names
     *  @isCenterSquareUsed
     *  purpose-> checks whether or not a played word uses the center square during the first round of the game
     *  @findWordOrientation
     *  purpose-> finds the orientation of selected words in order to more easily interpret letter positions
     *  @structurePlayedWord
     *  purpose-> structures a played word based on letter location in order to ensure that the word is in the correct order
     *  @getWordAsString
     *  purpose-> converts array of letter objects to string in order to validate word in the backend
     *  @isValidWord
     *  purpose-> shell of position checking functionality
     *      subset->
     *      @isWordConnectedToItself
     *      purpose-> ensures that the played word is connected is a single row, and not spread out throughout the board
     *      @isWordConnectedToLetterMass
     *      purpose-> ensures that the played word is connected to previously played words
     *  @convertBlankLetters
     *  purpose-> finds all blank letters and flags them in order to correctly compute letter values in the backend
     *  */
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
        //@note -> needs to be called after word is reordered, else letterObjects[0] may produce incorrect values.
        function isCenterSquareUsed() {
            for (var letterObject of letterObjects) {
                if (letterObject.columnLocation === 7 && letterObject.rowLocation === 7 && that.isFirstRound == true){
                    return true;
                }
            }
            return false;
        }

        function findWordOrientation() {

            if (letterObjects.length > 1 && Math.abs(letterObjects[0].rowLocation - letterObjects[1].rowLocation) !== 0) {
                wordOrientation = "vertical";
                for (var letter of letterObjects) {
                    letter.positionToCheck = letter.rowLocation;
                }
            } else {
                wordOrientation = "horizontal";
                for (var letterObject of letterObjects) {
                    letterObject.positionToCheck = letterObject.columnLocation;
                }
            }
        }

        function structurePlayedWord() {
            var sortedLetters = letterObjects;
            console.log(letterObjects);
            letterObjects.sort(function (a, b) {
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
            var letters = letterObjects.slice(0);

            function isWordConnectedToItself() {
                if (letters.length === 1) {
                    return true;
                }else{
                    for (var f = 0; f < letters.length - 1; f++) {
                        var rowCalculations = Math.abs(letters[f].rowLocation - letters[f + 1].rowLocation);
                        var colCalculations = Math.abs(letters[f].columnLocation - letters[f + 1].columnLocation);

                        if (rowCalculations !== 0 && colCalculations !== 0) {
                            return false;
                        } else if(rowCalculations > 1) {
                            for (var j = 1; j < rowCalculations; j++) {
                                if (!that.isSpaceTaken(letters[f].columnLocation, letters[f].rowLocation + j)) {
                                    return false;
                                }
                            }
                        } else if(colCalculations > 1) {
                            for (var k = 1; k < colCalculations; k++) {
                                if (!that.isSpaceTaken(letters[f].columnLocation + k, letters[f].rowLocation)) {
                                    return false;
                                }
                            }
                        }

                    }
                }
                return true;
            }

            function isWordConnectedToLetterMass() {
                var initialLetterColumn = letterObjects[0].columnLocation;
                var initialLetterRow = letterObjects[0].rowLocation;
                var finalLetterColumn = letterObjects[0].columnLocation;
                var finalLetterRow = letterObjects[0].rowLocation;

                for (var letter of letterObjects) {
                    if (initialLetterColumn == 0 || initialLetterRow == 0 || finalLetterColumn == 0 || finalLetterRow == 0) {
                        that.findWordConnectionPoints(letter, undefined, true);
                    } else if(initialLetterColumn == 14 || initialLetterRow == 14 || finalLetterColumn == 0 || finalLetterRow == 0) {
                        that.findWordConnectionPoints(letter, true, undefined);
                    } else {
                        that.findWordConnectionPoints(letter, true, true);
                    }
                }
            }

            if (that.isFirstRound) {
                return isWordConnectedToItself() && isCenterSquareUsed()
            } else {
                return isWordConnectedToItself() && isWordConnectedToLetterMass();
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

        if (that.currentWord === 0) {
            alert("The word you have entered is empty. Please try again.")
            return;
        }

        initLetterObjects();
        findWordOrientation();
        structurePlayedWord();
        console.log(letterObjects);

        if (isValidWord() === false) {
            alert("your letter placement in invalid, please try again");
        } else {
            convertBlankLetters();
            $.post("/PlayWord",
                {"wordPlayed":getWordAsString(), "letterPositionsCol": letterObjects[0].columnLocation, "letterPositionsRow":
                letterObjects[0].rowLocation, "wordOrientation": wordOrientation, "blankLetters": strBlankLetters},
                function(data, status){});
        }


        //TODO: if Word checks out, servlet return true & add letters to front end board
    },

    /**
     * @skipTurn
     * purpose-> skips the turn of the current player*/
    skipTurn: function() {},

    /**
     * @findWordConnectionPoints
     * purpose-> checks ScrabbleBoard to find previous words in order to check whether a played word is valid*/
    findWordConnectionPoints: function(letterToCheck, canSubtract, canAdd) {
        var col = letterToCheck.columnLocation;
        var row = letterToCheck.rowLocation;

        if (canAdd !== undefined && canSubtract !== undefined) {
            return this.isSpaceTaken(col + 1, row) || this.isSpaceTaken(col, row + 1) || this.isSpaceTaken(col - 1, row) || this.isSpaceTaken(col, row - 1);
        } else if (canAdd !== undefined && canSubtract === undefined) {
            return this.isSpaceTaken(col + 1, row) || this.isSpaceTaken(col, row + 1);
        } else if (canSubtract !== undefined && canAdd === undefined) {
            return this.isSpaceTaken(col - 1, row) || this.isSpaceTaken(col, row - 1);
        }
    },

    /**
     * @initButtons
     * purpose-> initializes the buttons used for skipping player turns, playing words, and exchanging letters*/
    initButtons: function() {
        var controlButtonHeight = this.game.world.height + 60;
        var that = this;
        var playWordButton = this.game.add.button(440, 640,'PlayWordButton', function() {
            if (confirm("Are you sure you want to play this word?")) {
                that.playWord();
            } else {
            }

        }, this, 2, 1, 0);
        var passTurnButton = this.game.add.button(this.game.world.centerX + playWordButton.width + 5, controlButtonHeight, 'PassTurnButton');
        var exchangeLettersButton = this.game.add.button(440 + 30, 640, 'SwapWordsButton', function() {
            that.exchangeLetters();
        });
        var quitGameButton = this.game.add.button(this.game.world.width - playWordButton.width, controlButtonHeight, 'QuitGameButton');
        playWordButton.anchor.setTo(0.5, 0.5);
        passTurnButton.anchor.setTo(0.5, 0.5);
        exchangeLettersButton.anchor.setTo(0.5, 0.5);
        quitGameButton.anchor.setTo(0.5, 0.5);
    },

    /**
     * @initScrabbleBoardTiles
     * purpose-> initializes the tilemap for the interface*/
    initScrabbleBoardTiles: function () {

        this.scrabbleTileMap = this.game.add.tilemap("ScrabbleBoardTileSet");
        this.scrabbleTileMap.addTilesetImage('ScrabbleBoardTileset', 'tileImage'); //first arg needs to match the image "name" from the JSON file
        this.scrabbleTileMap.addTilesetImage('ScrabbleAlphabetTileset', 'alphabetImage'); //first arg needs to match the image "name" from the JSON file
        this.scrabbleBoardLayer = this.scrabbleTileMap.createLayer('ScrabbleBoardLayer');
        this.scrabbleBoardLayer.resizeWorld();
        var boardImageTileMap = this.scrabbleTileMap.tilesets[this.scrabbleTileMap.getTilesetIndex('ScrabbleBoardTilesetImage')];
        var currentTile = this.scrabbleTileMap.getTile(this.scrabbleBoardLayer.getTileX(1) * this.SQUARE_SIZE, this.scrabbleBoardLayer.getTileY(1) * this.SQUARE_SIZE); 
    },

    /**
     * @exchangeLetters
     * purpose-> allows the player to exchange letters
     * Adds interface functionality for doing so
     * */
    exchangeLetters: function() {
        var that = this;
        var lettersToExchange = [];

        (function() {
            alert("click on the letters you would like to exchange");
            for (var letter of that.lettersOnRack) {
                that.isExchangingLetters = true;
                letter.originalKey = letter.key;
                letter.input.disableDrag(true);
                letter.input.disableSnap(true);
                letter.x = letter.originalPosition.x;
                letter.y = letter.originalPosition.y;
                letter.isSelectedToBeExchanged = false;
                letter.events.onInputDown.add(function(letter) {
                    if (that.letterKeys.indexOf(letter.key) !== -1){
                        letter.loadTexture(letter.key.concat("Selected"));
                        lettersToExchange.push(letter.name);
                    } else if(that.letterKeys.indexOf(letter.key) === -1) {
                        letter.loadTexture(letter.originalKey);
                        lettersToExchange.splice(lettersToExchange.indexOf(letter.name), 1);
                    }
                    console.log(lettersToExchange);
                }, this);
                }
        })();

    },

    /**
     * @managePlacedLetter
     * purpose-> ensures that that a user cannot put two letters on top of one another*/
    managePlacedLetter: function(sprite) {
        var posX = sprite.locationCol;
        var posY = sprite.locationRow;
        var that = this;

        function findLetterIntersections() {
            if(that.currentWord.length > 0) {
                for (var letter of that.currentWord) {
                    if (letter.locationCol === posX && letter.locationRow === posY) {
                        return false;
                    }
                }
            }
            return true;
        }

        function findPreviousWordIntersections() {
            return that.isSpaceTaken(posX, posY);
        }

        if (!findLetterIntersections() || !findPreviousWordIntersections()) {
            alert("invalid move, please try again");
            sprite.x = sprite.originalPosition.x;
            sprite.y = sprite.originalPosition.y;
            return false;
        }
        return true;
    },

    /**
     * @placeLetterOnBoard
     * purpose-> sets up letters to correctly manage their behavior, How the letter is placed on the board and is behavior on the board
     * */
    placeLetterOnBoard: function(sprite) {
        var letterImage = sprite;
        var that = this;

        function isSpaceUsed() {
            var xPos = that.marker.x;
            var yPos = that.marker.y;
            return (that.scrabbleBoardMap[that.positionMap[xPos]][that.positionMap[yPos]] = !"noLetter");
        }

        function setUpLetter() {
            that.dropLetter.play();

            if (that.marker.y < 600 && !isSpaceUsed()) {
                letterImage.x = that.marker.x;
                letterImage.y = that.marker.y;
                letterImage.locationCol = that.positionMap[letterImage.x];
                letterImage.locationRow = that.positionMap[letterImage.y];

                if (that.managePlacedLetter(letterImage) === false) {
                    return;
                }

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

                if ((letterImage.name != letterImage.key) && (letterImage.isBlankLetter) && that.isExchangingLetters === false) {
                    letterImage.loadTexture("_");
                }

                if (that.currentWord.indexOf(letterImage) !== -1) {
                    that.currentWord.splice(that.currentWord.indexOf(letterImage), 1);
                }
            }
        }
        setUpLetter();
    },

    /**
     *@removeLetterFromWord
     * purpose-> adds functionality to remove letters from word when removed from its position on the scrabbleboard
     * */
    removeLetterFromWord: function (sprite) {
        if (this.currentWord.indexOf(sprite) !== -1) {
            this.currentWord.splice(this.currentWord.indexOf(sprite), 1);
        }
    },

    /**
     * @deactivateButtons
     * purpose-> deactivates buttons when a player selects to exchange, play word, or skip turn
     * */
    deactivateButtons: function () {

    },

    /**
     * @initScrabbleRack
     * purpose-> initializes sprite images placed on rack*/
    initScrabbleRack: function () {
        console.log(this.playerRack);
        var lettersGroup = this.game.add.group();
        var that = this;

        for (var rackCol = 0; rackCol < 7; rackCol++) {
            var letterToPlace = this.playerRack[rackCol];
            var tile = this.scrabbleTileMap.getTile(rackCol, 17);
            var sprite = this.game.add.sprite(rackCol * 41, 640, letterToPlace);
            this.lettersOnRack.push(sprite);

            sprite.originalPosition = {
                x: sprite.x,
                y: sprite.y
            };
            sprite.inputEnabled = true;
            sprite.input.enableDrag(true);
            sprite.name = letterToPlace;
            sprite.isBlankLetter = (sprite.name == "_");
            sprite.events.onInputUp.add(function(sprite) {this.placeLetterOnBoard(sprite);}, this);
            sprite.events.onInputDown.add(function (sprite) {this.removeLetterFromWord(sprite);}, this)
        }
    },

    /**
     * @exchangeBlankLetter
     * purpose-> sets up interface functionality to exchange blank letters
     * */
    exchangeBlankLetter: function(sprite) {
        this.currentBlankLetter.loadTexture(sprite.key);
        this.currentBlankLetter.visible = true;
        this.exchangableLetters.visible = false;
    },

    //Make an AJAX call using JQuery to get the JSON for the current state of the scrabble board and convent it to a Java script object
    /**
     *@getScrabbleBoard
     * purpose-> adds ajax call to get the current state of the scrabbleboard, converting it to js object*/
    getScrabbleBoard: function () {
        var board = null;
        $.post("/GameInquiry", {inquiryType: "scrabbleBoard"}, function (data, status) {
            board = JSON.parse(data);
        })
    },

    /**
     * @getRackSuccess
     * purpose-> gets JSON for rack from the backend*/
    getRackSuccess: function(data) {
        console.log(this);
        console.log(data);
        this.playerRack = JSON.parse(data);
        this.initScrabbleRack();
    },

    /**
     * @getRackFailure
     * purpose-> called when backend fails to send rack JSON*/
    getRackFailure: function() {
        console.log("Call to GetPlayRack failed");
    },

    /**
     * @finishTurn
     * purpose-> resets constructs used on the front end, for the next player's turn*/
    finishTurn: function() {
        this.currentWord = [];
        this.exchangableLetters = null;
        this.currentBlankLetter = null;
        this.isFirstRound = true;
        this.isValidWord = true;
        this.isFirstRound = false;
    },

    create: function () {
        this.dropLetter = this.game.add.audio('letterPlace');
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

