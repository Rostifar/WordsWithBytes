/**
 * Created by Dad and Ross on 5/28/2016.
 */
WordsWithBytes.WaitForPlayers = function(game) {};

var gameCode;

WordsWithBytes.WaitForPlayers.prototype = {

    init: function (gameID) {
        gameCode = gameID;
    },

    create: function() {

        this.scale.fullScreenScaleMode = Phaser.ScaleManager.EXACT_FIT;

        var backgroundImage = this.add.sprite(this.game.world.centerX, this.game.world.centerY, 'space-background');
        backgroundImage.anchor.setTo(0.5, 0.5);

        var bannerFont = "24px Arial";
        var gameSelection = this.game.add.text(this.game.world.centerX, this.game.world.centerY / 4, "Words with Bytes - Your game code is: " + gameCode , {font: bannerFont, fill: "#eeeeee", stroke: "#535353", strokeThickness: 15});
        gameSelection.anchor.set(0.5);

      /*  newGameButton = this.game.add.button(this.game.world.centerX, this.game.world.centerY * (0.55), 'startNewGameButton', startNewGameOnClick, this, 2, 1, 0);
        newGameButton.anchor.setTo(0.5, 0.5);

        resumeGameButton = this.game.add.button(this.game.world.centerX, this.game.world.centerY, 'resumeExistingGameButton',   resumeGameOnClick, this, 2, 1, 0);
        resumeGameButton.anchor.setTo(0.5, 0.5);*/
    }
};