/**
 * Created by ross on 2/24/16.
 */
(function(WordsWithBytes, game) {

    function Rack() {
        Rack.createRackLocations();
    }

    Rack.createRackLocations = function() {

        (function() {
            var counter1, counter2;
             counter1 = 0;
             counter2 = 0;

            Rack.rackLocations.push(game.world.centerX);

            while (counter1 < 3) {
                Rack.rackLocations.push(game.world.centerX + (Math.floor(20 * i)));
                counter1 = +1;
            }
            while (counter2 < 1) {
                Rack.rackLocations.push(game.world.centerX + (Math.floor(-20 * j)));
                counter2 += 1;
            }
        })();
    };

    Rack.prototype = {
        gameRack: [],
        numberOfLettersOnRack: Rack.gameRack.size(),
        rackLocations: []
    };

    Rack.prototype.addLetterToRack = function(letterToAdd) {
        Rack.gameRack.push(letterToAdd)
    };

    WordsWithBytes.Rack = Rack;

})(this);







