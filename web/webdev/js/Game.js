/**
 * Created by ross on 1/31/16.
 */
"use strict";
WordsWithBytes.Game = function(game){
    var wg = WordsWithBytes.Game;

    wg.scrabbleBoardLayer = null;
    wg.marker = null;
    wg.SQUARE_SIZE = 40;
    wg.positionMap = {};
    wg.currentWord = [];
    wg.lettersOnRack = [];
    wg.scrabbleBoardMap = [[]];
    wg.exchangableLetters = null;
    wg.currentBlankLetter = null;
    wg.isFirstRound = true;
    wg.dropLetter = null;
    wg.letterKeys = ["A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"];
    wg.isExchangingLetters = false;
};


WordsWithBytes.Game.getMessage = function(gameJson) {
    var proto = WordsWithBytes.Game.prototype;
    var wg = WordsWithBytes.Game;
    WordsWithBytes.Player = gameJson.players[WordsWithBytes.Player.indx];
    proto.initScrabbleRack(WordsWithBytes.Player.rack);


    if (WordsWithBytes.Player !== gameJson.currentPlayer) {
        alert("Its not your turn yet. Please wait for other players to finish");
        proto.deactivateButtons();
    } else {
        alert("its your turn! please select a move");
        proto.activateButtons();
    }

    
    wg.scrabbleBoardMap = gameJson.scrabbleBoard.board;//TODO: get letter pictures for each letters
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
        var wg = WordsWithBytes.Game;

        for (var i = 0; i < 15; i++) {
            wg.positionMap[wg.SQUARE_SIZE * i] = i;
        }
    },

    /**
     * @populateScrabbleBoard
     * #purpose -> sets up a blank scrabbleBoard.
     * Allows the frontend to check whether a played word is valid or not
     * */
    populateScrabbleBoard: function() {
        var wg = WordsWithBytes.Game;
        for(var i = 0; i < 15; i++) {
           wg.scrabbleBoardMap[i] = [];
           for (var g = 0; g < 15; g++) {
              wg.scrabbleBoardMap[i][g] = "noLetter";
          }
       }
    },

    /**
     * @isSpaceTaken
     * param-> col = user selected column on ScrabbleBoard; row = user selected row on ScrabbleBoard
     * purpose-> enables quick functionality for checking whether a selected position already has been used
     * */
    isSpaceTaken: function(col, row) {
        var wg = WordsWithBytes.Game;
        return wg.scrabbleBoardMap[col][row] !== "noLetter";
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
        var background = game.add.sprite(160, 160, "square");
        var placementCoordinates = [200, 240, 280, 320, 360];
        var wg = WordsWithBytes.Game;
        wg.exchangableLetters = game.add.group();
        wg.exchangableLetters.add(background);

        function actionOnPress(sprite) {
            sprite.inputEnabled = true;
            sprite.events.onInputUp.add(function() {that.exchangeBlankLetter(sprite);}, this);
        }

        while (positionIndex < placementCoordinates.length) {
            var currentX = 200;
            for (var i = 0; currentX <= placementCoordinates[placementCoordinates.length - 1]; i++) {
                var sprite = game.add.sprite(currentX, placementCoordinates[positionIndex], wg.letterKeys[letterIndex]);
                wg.exchangableLetters.add(sprite);
                actionOnPress(sprite);
                currentX = placementCoordinates[i + 1];
                letterIndex++;
            }
            positionIndex++;
        }
        var zSprite = game.add.sprite(280, 400, "Z");
        actionOnPress(zSprite);
        wg.exchangableLetters.add(zSprite);
        wg.exchangableLetters.visible = false;
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
        var wg = WordsWithBytes.Game;
        var blankLetters = [wg.currentWord.length];
        var strBlankLetters;
        var stringRow;
        var stringCol;

        function initLetterObjects() {
            for (var i = 0; i < wg.currentWord.length; i++) {
                letterObjects[i] = {
                    letterName: wg.currentWord[i].name,
                    columnLocation: wg.currentWord[i].locationCol,
                    rowLocation: wg.currentWord[i].locationRow
                };
            }
        }
        //@note -> needs to be called after word is reordered, else letterObjects[0] may produce incorrect values.
        function isCenterSquareUsed() {
            for (var letterObject of letterObjects) {
                if (letterObject.columnLocation === 7 && letterObject.rowLocation === 7 && wg.isFirstRound == true){
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
                var wg = WordsWithBytes.Game;

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

            if (wg.isFirstRound) {
                return isWordConnectedToItself() && isCenterSquareUsed()
            } else {
                return isWordConnectedToItself() && isWordConnectedToLetterMass();
            }
        }

        function convertBlankLetters() {
            for (var i = 0; i < wg.currentWord.length; i++) {
                if (wg.currentWord[i].isBlankLetter) {
                    blankLetters[i] = wg.currentWord[i].key;
                } else {
                    blankLetters[i] = "#";
                }
            }
            strBlankLetters = blankLetters.join("");
        }

        if (wg.currentWord === 0) {
            alert("The word you have entered is empty. Please try again.")
            return;
        }
        
        function getIndividualPositions() {
            var positionsRow = [];
            var positionsCol = [];
            
            for (var letterObject of letterObjects) {
                positionsCol.push(letterObject.columnLocation.toString());
                positionsRow.push(letterObject.rowLocation.toString());
            }
            stringCol = positionsCol.join();
            stringRow = positionsRow.join();
        }

        initLetterObjects();
        findWordOrientation();
        structurePlayedWord();

        if (isValidWord() === false) {
            alert("your letter placement in invalid, please try again");
        } else {
            convertBlankLetters();
            getIndividualPositions();

            $.post("/PlayWord",
                {"wordPlayed":getWordAsString(), "letterPositionsCol": stringCol, "letterPositionsRow":
                stringRow, "wordOrientation": wordOrientation, "blankLetters": strBlankLetters},
                function(data, status){
                    while (!data.finish) {
                        that.deactivateButtons().bind(that);
                    }
                });
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
        var that = this;
        WordsWithBytes.Game.playWordButton = game.add.button(400, 620,'PlayWordButton', function() {
            if (confirm("Are you sure you want to play this word?")) {
                that.playWord();
            }

        }, this, 2, 1, 0);
        WordsWithBytes.Game.passTurnButton = game.add.button(500, 620, 'PassTurnButton');
        WordsWithBytes.Game.exchangeLettersButton = game.add.button(400, 660, 'SwapWordsButton', function() {
            that.exchangeLetters();
        });
        WordsWithBytes.Game.quitGameButton = game.add.button(500, 660, 'QuitGameButton');

        WordsWithBytes.Game.playWordButton.anchor.setTo(0.5, 0.5);
        WordsWithBytes.Game.passTurnButton.anchor.setTo(0.5, 0.5);
        WordsWithBytes.Game.exchangeLettersButton.anchor.setTo(0.5, 0.5);
        WordsWithBytes.Game.quitGameButton.anchor.setTo(0.5, 0.5);
    },

    /**
     * @initScrabbleBoardTiles
     * purpose-> initializes the tilemap for the interface*/
    initScrabbleBoardTiles: function () {
        var wg = WordsWithBytes.Game;

        wg.scrabbleTileMap = game.add.tilemap("ScrabbleBoardTileSet");
        wg.scrabbleTileMap.addTilesetImage('ScrabbleBoardTileset', 'tileImage'); //first arg needs to match the image "name" from the JSON file
        wg.scrabbleTileMap.addTilesetImage('ScrabbleAlphabetTileset', 'alphabetImage'); //first arg needs to match the image "name" from the JSON file
        wg.scrabbleBoardLayer = wg.scrabbleTileMap.createLayer('ScrabbleBoardLayer');
        wg.scrabbleBoardLayer.resizeWorld();
        var boardImageTileMap = wg.scrabbleTileMap.tilesets[wg.scrabbleTileMap.getTilesetIndex('ScrabbleBoardTilesetImage')];
        var currentTile = wg.scrabbleTileMap.getTile(wg.scrabbleBoardLayer.getTileX(1) * wg.SQUARE_SIZE, wg.scrabbleBoardLayer.getTileY(1) * wg.SQUARE_SIZE);
    },

    /**
     * @exchangeLetters
     * purpose-> allows the player to exchange letters
     * Adds interface functionality for doing so
     * */
    exchangeLetters: function() {
        var that = this;
        var lettersToExchange = [];
        var wg = WordsWithBytes.Game;

        (function() {
            alert("click on the letters you would like to exchange");
            for (var letter of wg.lettersOnRack) {
                wg.isExchangingLetters = true;
                letter.originalKey = letter.key;
                letter.input.disableDrag(true);
                letter.input.disableSnap(true);
                letter.x = letter.originalPosition.x;
                letter.y = letter.originalPosition.y;
                letter.isSelectedToBeExchanged = false;
                letter.events.onInputDown.add(function(letter) {
                    if (wg.letterKeys.indexOf(letter.key) !== -1){
                        letter.loadTexture(letter.key.concat("Selected"));
                        lettersToExchange.push(letter.name);
                    } else if(wg.letterKeys.indexOf(letter.key) === -1) {
                        letter.loadTexture(letter.originalKey);
                        lettersToExchange.splice(lettersToExchange.indexOf(letter.name), 1);
                    }
                    console.log(lettersToExchange);
                }, this);
                }
        })();

        $.post("/ExchangeLetters", {"lettersToExchange" : lettersToExchange});
    },

    /**
     * @managePlacedLetter
     * purpose-> ensures that that a user cannot put two letters on top of one another*/
    managePlacedLetter: function(sprite) {
        var posX = sprite.locationCol;
        var posY = sprite.locationRow;
        var that = this;
        var wg = WordsWithBytes.Game;

        function findLetterIntersections() {
            if(wg.currentWord.length > 0) {
                for (var letter of wg.currentWord) {
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
        var wg = WordsWithBytes.Game;

        function isSpaceUsed() {
            var xPos = wg.marker.x;
            var yPos = wg.marker.y;
            return (wg.scrabbleBoardMap[wg.positionMap[xPos]][wg.positionMap[yPos]] = !"noLetter");
        }

        function setUpLetter() {
            var wg = WordsWithBytes.Game;
            wg.dropLetter.play();

            if (wg.marker.y < 600 && !isSpaceUsed()) {
                letterImage.x = wg.marker.x;
                letterImage.y = wg.marker.y;
                letterImage.locationCol = wg.positionMap[letterImage.x];
                letterImage.locationRow = wg.positionMap[letterImage.y];

                if (that.managePlacedLetter(letterImage) === false) {
                    return;
                }

                wg.currentWord.push(letterImage);

                if (letterImage.isBlankLetter == true) {
                    wg.exchangableLetters.visible = true;
                    letterImage.visible = false;
                    wg.currentBlankLetter = letterImage;
                }

            } else {
                letterImage.x = letterImage.originalPosition.x;
                letterImage.y = letterImage.originalPosition.y;
                letterImage.locationCol = null;
                letterImage.locationRow = null;

                if ((letterImage.name != letterImage.key) && (letterImage.isBlankLetter) && wg.isExchangingLetters === false) {
                    letterImage.loadTexture("_");
                }

                if (wg.currentWord.indexOf(letterImage) !== -1) {
                    wg.currentWord.splice(wg.currentWord.indexOf(letterImage), 1);
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
        var wg = WordsWithBytes.Game;
        if (wg.currentWord.indexOf(sprite) !== -1) {
            wg.currentWord.splice(wg.currentWord.indexOf(sprite), 1);
        }
    },

    /**
     * @deactivateButtons
     * purpose-> deactivates buttons when a player selects to exchange, play word, or skip turn
     * */
    deactivateButtons: function () {
        //game.
        WordsWithBytes.Game.exchangeLettersButton.input.enabled = false;
        WordsWithBytes.Game.playWordButton.input.enabled = false;
        WordsWithBytes.Game.passTurnButton.input.enabled = false;
    },
    
    activateButtons: function() {
        WordsWithBytes.Game.exchangeLettersButton.input.enabled = true;
        WordsWithBytes.Game.playWordButton.input.enabled = true;
        WordsWithBytes.Game.passTurnButton.input.enabled = true;
    },

    /**
     * @initScrabbleRack
     * purpose-> initializes sprite images placed on rack*/
    initScrabbleRack: function (playerRack) {
        var wg = WordsWithBytes.Game;

        for (var rackCol = 0; rackCol < 7; rackCol++) {
            var letterToPlace = playerRack[rackCol];
            var tile = wg.scrabbleTileMap.getTile(rackCol, 17);
            var sprite = game.add.sprite(rackCol * 41, 640, letterToPlace);

            wg.lettersOnRack.push(sprite);

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
        var wg = WordsWithBytes.Game;
        wg.currentBlankLetter.loadTexture(sprite.key);
        wg.currentBlankLetter.visible = true;
        wg.exchangableLetters.visible = false;
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
        var wg = WordsWithBytes.Game;
        wg.dropLetter = game.add.audio('letterPlace');
        this.populatePositionMap();
        this.populateScrabbleBoard();
        this.initScrabbleBoardTiles();
        this.initButtons();
        this.setupBlankLetterExchangeSystem();

        wg.marker = game.add.graphics();
        wg.marker.lineStyle(2, 0x000000, 1);
        wg.marker.drawRect(0, 0, wg.SQUARE_SIZE, wg.SQUARE_SIZE);
    },

    update: function () {
        var wg = WordsWithBytes.Game;
        if (wg.scrabbleBoardLayer != null) {
            wg.marker.x = wg.scrabbleBoardLayer.getTileX(game.input.activePointer.worldX) * wg.SQUARE_SIZE;
            wg.marker.y = wg.scrabbleBoardLayer.getTileY(game.input.activePointer.worldY) * wg.SQUARE_SIZE;
        }
    },

    render: function() {
        var wg = WordsWithBytes.Game;
        if (wg.scrabbleBoardLayer != null) {
            game.debug.text('Location: ' + wg.marker.x + "," + wg.marker.y, 10, 10, '#efefef');
        }
    }
};

