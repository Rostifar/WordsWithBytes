/**
 * Created by ross on 2/24/16.
 */
"use strict";

(function(WordsWithBytes) {
    function Rack(game) {
        Rack.gameRack = [];
        Rack.locations = [];
        Rack.yLocation = game.height - 40;
        createRackLocations();
    }

    function createRackLocations() {
        var counter1, counter2;
        counter1 = 0;
        counter2 = 0;

        WordsWithBytes.Rack.locations.push(game.world.centerX);

        while (counter1 < 3) {
            ++counter1;
            Rack.locations.push(game.world.centerX + (50 * counter1));
        }
        while (counter2 < 3) {
            ++counter2;
            Rack.locations.push(game.world.centerX - (50 * counter2));
        }
    }

    function shiftLetterPositions() {
        for (var i = 0; i < Rack.gameRack.length; i++) {
            Rack.gameRack[i].x = Rack.locations[i];
        }
    }

    Rack.prototype.getInterfaceMechanicsReference = function(interfaceMechanicsReference) {
        this.interfaceMechanics = interfaceMechanicsReference;
    };

    function analyzeLetterPlacement() {
        if (this.interfaceMechanics.isInBoardProximity(Rack.currentLetter)) {
            this.interfaceMechanics.searchForClosestSquare(Rack.currentLetter);
        } else {
            Rack.currentLetter.x = Rack.currentLetter.positionOnRack;
            Rack.currentLetter.y = Rack.yLocation;
        }
    }

    function setCurrentLetter(letter) {
        Rack.currentLetter = letter;
    }

    Rack.prototype.getNumberOfLettersOnRack = function () {
        return Rack.numberOfLettersOnRack;
    };

    Rack.prototype.addLetterToRack = function (imageKey) {
        var xLocation = Rack.locations[Rack.gameRack.length];
        shiftLetterPositions();
        var letter = game.add.sprite(xLocation, Rack.yLocation, imageKey);
        letter.positionOnRack = xLocation;
        letter.anchor.setTo(0.5);
        letter.inputEnabled = true;
        letter.input.enableDrag(true);
        letter.events.onDragStart.add(setCurrentLetter, letter);
        letter.events.onInputUp.add(analyzeLetterPlacement, this);

        Rack.gameRack.push(letter);
    };

    Rack.prototype.getLetterBeingSelected = function(pointer) {
        for (var i = 0; i < Rack.gameRack.length; i++) {
            if (Rack.gameRack[i].isDragged) {
                //pointer.currentLetter = Rack.gameRack[i];
            }
        }
    };

    Rack.prototype.removeLetterFromRack = function (letterToRemove) {
        var previousPosition = Rack.gameRack.indexOf(letterToRemove) - 1;
        var currentPosition = previousPosition + 1;
        Rack.gameRack.splice(Rack.gameRack.indexOf(previousPosition, currentPosition));
    };
    WordsWithBytes.Rack = Rack;
})(this);

