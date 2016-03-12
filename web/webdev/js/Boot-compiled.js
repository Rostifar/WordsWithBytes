var WordsWithBytes = {};

WordsWithBytes.Boot = function (game) {};

WordsWithBytes.Boot.prototype = {

    scaleStage: function () {

        if (this.game.device.desktop) {

            this.scale.scaleMode = Phaser.ScaleManager.SHOW_ALL;
            this.scale.minHeight = this.gameHeight;
            this.scale.minWidth = this.gameWidth / 2;
            this.scale.maxHeight = this.gameHeight;
            this.scale.maxWidth = this.gameWidth;

            this.scale.pageAlignHorizontally = true;
            this.scale.pageAlignVertically = true;
        } else {

            this.scale.scaleMode = Phaser.ScaleManager.EXACT_FIT;
        }
    },

    init: function () {

        this.scale.pageAlignVertically = true;
        this.scale.pageAlignHorizontally = true;
        this.scale.refresh();
    },

    preload: function () {
        //assets used for creation of loading screen
        this.load.image('WordsWithBytes-logo', 'assets/images/WordWithBytesLogo.png');
    },

    create: function () {
        this.game.stage.backgroundColor = '#659EC7';
        //scaling options

        this.state.start('Preload');
    },

    enterIncorrectOrientation: function () {

        WordsWithBytes.orientated = false;
        document.getElementById('orientation').style.display = 'block';
    },

    leaveIncorrectOrientation: function () {

        WordsWithBytes.orientated = true;
        document.getElementById('orientation').style.display = 'none';
    }
};

//# sourceMappingURL=Boot-compiled.js.map