var WordsWithBytes = {};
WordsWithBytes.Boot = function(game){};

WordsWithBytes.Boot.prototype = {

    scaleStage: function () {

        if (this.game.device.desktop) {

            this.scale.scaleMode = Phaser.ScaleManager.EXACT_FIT;
            this.scale.minHeight = this.gameHeight;
            this.scale.minWidth = this.gameWidth / 2;
            this.scale.maxHeight = this.gameHeight;
            this.scale.maxWidth = this.gameWidth;
            this.scale.pageAlignHorizontally = true;
            this.scale.pageAlignVertically = true;
        } else {

            this.game.scale.pageAlignHorizontally = true;
            this.game.scale.pageAlignVertically = true;

        }

    },

   

    preload: function () {
        this.load.image('WordsWithBytes-logo', 'assets/images/WordWithBytesLogo.png');
    },

    create: function () {
        this.game.stage.backgroundColor = '#659EC7';
        this.state.start('Preload');
    }
};