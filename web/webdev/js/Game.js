/**
 * Created by ross on 1/31/16.
 */

WordsWithBytes.Game = function(game) {};
new n
    WordsWithBytes.Game.prototype = {

        initConstructs: function(boardImage) {
            this.rack = new Rack(this.game);
            this.interfaceMechanics = new InterfaceMechanics(this.game, boardImage);
        },

        calculateBoardProperties: function(boardImage) {

        },

        create: function () {
            this.game.add.sprite(0, 0, 'space-background');
            var boardImage = this.game.add.sprite(this.game.world.centerX, this.game.world.centerY, 'scrabbleBoard');
            boardImage.anchor.setTo(0.5);
            this.initConstructs(boardImage);
            this.rack.getInterfaceMechanicsReference(this.interfaceMechanics);
            this.interfaceMechanics.calculateCenterSquares();
            this.rack.addLetterToRack('bl', this.interfaceMechanics);
            this.rack.addLetterToRack('a', this.interfaceMechanics);
        },

        update: function () {
        }
    };

