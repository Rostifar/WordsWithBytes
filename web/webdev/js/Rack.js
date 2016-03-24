/**
 * Created by ross on 2/24/16.
 */
(function(WordsWithBytes) {
		"use strict";

		class Rack {
            constructor(game) {
                this.game = game;
            }

            init() {
                this.gameRack = [];
                this.numberOfLettersOnRack = this.gameRack.size();
                this.rackLocations = [];

                function createRackLocations() {
                    var counter1, counter2;
                    counter1 = 0;
                    counter2 = 0;

                    this.rackLocations.push(game.world.centerX);

                    while (counter1 < 3) {
                        this.rackLocations.push(game.world.centerX + (20 * counter1));
                        counter1 = +1;
                    }
                    while (counter2 < 3) {
                        this.rackLocations.push(game.world.centerX + (-20 * counter2));
                        counter2 += 1;
                    }
                }
            }

            numberOfLettersOnRack() {
                return this.numberOfLettersOnRack;
            }

            addLetterToRack(letterToAdd) {
                this.gameRack.push(letterToAdd);
            };

            removeLetterFromRack(letterToRemove) {
                let previousPosition = this.gameRack.indexOf(letterToRemove) - 1;
                let currentPosition = previousPosition + 1;
                this.gameRack.splice(this.gameRack.indexOf(previousPosition, currentPosition))
            }
        }
		WordsWithBytes.Rack = Rack;
})();
