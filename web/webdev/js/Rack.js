/**
 * Created by ross on 2/24/16.
 */
"use strict";

    var game, gameRack, locations, yLocation, currentLetter, xLocation;
    game = scrabbleGame.getGame;
    gameRack = [];
    locations = [];
    yLocation = game.height - 40;
    xLocation = locations[gameRack.length];

    function setCurrentLetter(letter) {
        currentLetter = letter;
    }

    function analyzeLetterPlacement() {
        if (interfaceMechanics.isInBoardProximity(currentLetter)) {
            interfaceMechanics.searchForClosestSquare(currentLetter)
        } else {
            currentLetter.x = currentLetter.positionOnRack;
            currentLetter.y = yLocation;
        }
    }

    function shiftLetterPositions() {
        for (var i = 0; i < gameRack.length; i++) {
            gameRack[i].x = locations[i];
        }
    }

    function addLetterToRack(imageKey) {
        shiftLetterPositions();
        var letter = game.add.sprite(xLocation, yLocation, imageKey);
        letter.positionOnRack = xLocation;
        letter.anchor.setTo(0.5);
        letter.inputEnabled = true;
        letter.input.enableDrag(true);
        letter.events.onDragStart.add(setCurrentLetter, letter);
        letter.events.onInputUp.add(analyzeLetterPlacement, this);
        gameRack.push(letter);
    }

    function removeLetterFromRack(letterToRemove) {
        var previousPosition = gameRack.indexOf(letterToRemove) - 1;
        var currentPosition = previousPosition + 1;
        gameRack.splice(gameRack.indexOf(previousPosition, currentPosition));
    }

    return {
        initializeRackLocations: createRackLocations(),
        getNumberOfLettersOnRack: gameRacik.length,
        getLetterToAdd: function(imageKey) {
            addLetterToRack(imageKey);
        },
        getLetterToRemove: function(letterToRemove) {
           removeLetterFromRack(letterToRemove);
        }
    }
