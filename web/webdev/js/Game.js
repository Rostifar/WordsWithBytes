/**
 * Created by ross on 1/31/16.
 */
var Game = WordsWithBytes.Game;

WordsWithBytes.Game = function(game) {};

Game = {
}

// Gets letters selected by backend and creates an array of selected letter images by using selected letters as keys.
Game.method('getLetters', function (listOfLetterKeys) {

    var letters = [];

    for (var i = 0; listOfLetterKeys.length; i+1) {
        letters.push(cache.image(listOfLetterKeys[i]));
    }
    return letters;
});

WordsWithBytes.Game.prototype = {



    getCurrentLetter: function() {

        //if (pointer.onHold() && Math.abs(this.pointerX - letter.x) < 10 && Math.abs(this.pointerY - letter.y)) {
          //  this.currentLetter = letter;
        //}
    },

    createScore: function(scrabbleBoardWidth) {

        var scoreFont = "50px Arial";
        var xLocation = scrabbleBoardWidth / 2;
        this.scoreLabel = this.game.add.text(xLocation, 5, "0", {font: scoreFont, fill: "#ffffff", stroke: "#535353", strokeThickness: 15});
        this.scoreLabel.align = 'center';
    },

    createLetters: function() {

    },


    /** @update


    /*
     * Added properties which place the background of the game and the board on the game canvas.
     * Along with formatting these objects on the canvas.
     * */
    create: function () {

        this.game.add.sprite(0, 0, 'space-background');
        var boardImage = this.game.add.sprite(this.game.world.centerX, this.game.world.centerY, 'scrabbleBoard');
        boardImage.anchor.setTo(0.5);
        this.scrabbleBoard = new ScrabbleBoard(this.game, boardImage.width, boardImage.height);
        this.createScore(this.scrabbleBoard.calculateMaxBoardWidth());
        this.interfaceMechanics = new InterfaceMechanics(this.scrabbleBoard);


    },

    update: function () {



        this.pointerX = this.game.input.x;
        this.pointerY = this.game.input.y;


        if((this.pointerX >= this.scrabbleBoard.calculateExcessPixelsX && this.pointerX <= this.scrabbleBoard.calculateMaxBoardWidth) && this.pointerY >= this.scrabbleBoard.calculateExcessPixelsY && this.pointerY <= this.scrabbleBoard.calculateExcessPixelsY) {
        }
    }


};
