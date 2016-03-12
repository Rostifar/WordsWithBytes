/**
 * Created by ross on 1/31/16.
 */

WordsWithBytes.Game = function(game){};

WordsWithBytes.Game.prototype = {

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
        var scrabbleBoard = new ScrabbleBoard(this.game, boardImage.width, boardImage.height);
        this.createScore(scrabbleBoard.calculateMaxBoardWidth());
        this.rack = new Rack();
        this.interfaceMechanics = new InterfaceMechanics(this.scrabbleBoard);



    },

    update: function () {
        this.pointerX = this.game.input.x;
        this.pointerY = this.game.input.y;
    }


};
