/**
 * Created by ross on 1/31/16.
 */

WordsWithBytes.Game = function(game) {};

    WordsWithBytes.Game.prototype = {

        create: function () {
            this.game.add.sprite(0, 0, 'space-background');
            var boardImage = this.game.add.sprite(this.game.world.centerX, this.game.world.centerY, 'scrabbleBoard');
            boardImage.anchor.setTo(0.5);
            this.rack = new Rack(this.game);
            this.scrabbleBoard = new ScrabbleBoard(this.game, boardImage);
            this.interfaceMechanics = new InterfaceMechanics();
            this.rack.getInterfaceMechanicsReference(this.interfaceMechanics);
            this.interfaceMechanics.getScrabbleBoardInstance(this.scrabbleBoard);
            this.interfaceMechanics.calculateCenterSquares();
            this.rack.addLetterToRack('bl', this.interfaceMechanics);
            this.rack.addLetterToRack('a', this.interfaceMechanics);
        },

        update: function () {
        }
    };

