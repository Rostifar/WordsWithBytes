/**
 * Created by ross on 2/24/16.
 */
"use strict";

var rack = (function () {
    var game, gameRack, locations, yLocation, currentLetter, xLocation;

    function getGameInstance(gameInstanceReference) {
        game = gameInstanceReference;
    }
    gameRack = [];
    locations = [];
    yLocation = game.height - 40;
    xLocation = locations[gameRack.length];

    function createRackLocations() {
        var counter1, counter2;
        counter1 = 0;
        counter2 = 0;

        locations.push(game.world.centerX);

        while (counter1 < 3) {
            ++counter1;
            locations.push(game.world.centerX + (50 * counter1));
        }
        while (counter2 < 3) {
            ++counter2;
            locations.push(game.world.centerX - (50 * counter2));
        }
    }

    function setCurrentLetter(letter) {
        currentLetter = letter;
    }

    function analyzeLetterPlacement() {
        if (interfaceMechanics.isInBoardProximity(currentLetter)) {
            interfaceMechanics.searchForClosestSquare(currentLetter);
        } else {
            currentLetter.x = currentLetter.positionOnRack;
            currentLetter.y = Rack.yLocation;
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
        getGameInstance: function(gameInstance) {
            getGameInstance(gameInstance);
        },
        initializeRackLocations: createRackLocations(),
        getNumberOfLettersOnRack: gameRack.length,
        getCurrentLetter: currentLetter,
        getLetterToAdd: function(imageKey) {
            addLetterToRack(imageKey);
        },
        getLetterToRemove: function(letterToRemove) {
           removeLetterFromRack(letterToRemove);
        }
    }
})();
