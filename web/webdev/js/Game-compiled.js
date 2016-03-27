/**
 * Created by ross on 1/31/16.
 */
var Game = WordsWithBytes.Game;

WordsWithBytes.Game = function (game) {};

// Gets letters selected by backend and creates an array of selected letter images by using selected letters as keys.
function getLetters(listOfLetterKeys) {

    var letters = [];

    for (var i = 0; listOfLetterKeys.length; i + 1) {
        letters.push(cache.image(listOfLetterKeys[i]));
    }
    return letters;
}

WordsWithBytes.Game.prototype = {

    /** @update
      /*
     * Added properties which place the background of the game and the board on the game canvas.
     * Along with formatting these objects on the canvas.
     * */
    create: function () {

        this.game.add.sprite(0, 0, 'space-background');
        var boardImage = this.game.add.sprite(this.game.world.centerX, this.game.world.centerY, 'scrabbleBoard');
        boardImage.anchor.setTo(0.5);
        var rack = new Rack(this.game);
        var scrabbleBoard = new ScrabbleBoard(this.game, boardImage);

        rack.addLetterToRack('bl');
        rack.addLetterToRack('a');
    },

    update: function () {

        this.pointerX = this.game.input.x;
        this.pointerY = this.game.input.y;
    }

};

//# sourceMappingURL=Game-compiled.js.map