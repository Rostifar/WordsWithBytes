/**
 * Created by ross on 1/31/16.
 */

WordsWithBytes.MainMenu = function(game) {};

WordsWithBytes.MainMenu.prototype = {

    create: function() {

        this.music = this.add.audio('introMusic');
        this.scale.fullScreenScaleMode = Phaser.ScaleManager.EXACT_FIT;
        var backgroundImage = this.add.sprite(this.camera.width / 2, this.game.camera.height / 2, 'scrabbleSample');
        backgroundImage.anchor.setTo(0.5, 0.5);

        var blurX = this.add.filter('BlurX');
        var blurY = this.add.filter('BlurY');

        backgroundImage.filters = [blurX, blurY];

        this.add.sprite(200, this.game.world.centerY, 'player1Button');
        this.add.sprite(400, this.game.world.centerY, 'player2Button');
        this.add.sprite(600, this.game.world.centerY, 'player3Button');

        this.state.start('Game');
    }
}