/**
 * Created by ross on 3/17/16.
 */

WordsWithBytes.Register = function(game) {

    this.startNewGame = function() {
        var username = prompt("enter a username");
        $.post("/StartGame", {username: username}, function(data, status){})
            .success(function() {
                game.state.start('GameLobby');
            });
    };

    this.setUpGame = function() {
        "use strict";
        var gameCode = prompt("Enter a game code");
    }
};

WordsWithBytes.Register.prototype = {
    create: function() {
        var that = this;
        var backgroundImage = this.add.sprite(this.game.world.centerX, this.game.world.centerY, 'space-background');
        var scoreFont = "60px Arial";
        var GameSelection = this.game.add.text(this.game.world.centerX, this.game.world.centerY / 4, "Select A Game Mode", {font: scoreFont, fill: "#ffffff", stroke: "#535353", strokeThickness: 15});
        GameSelection.anchor.set(0.5);

        var newGame = this.game.add.button(this.game.world.centerX, this.game.world.centerY - 100, 'StartGameButton', function() {
            that.startNewGame();
        });
      /*  var existingGame = this.game.add.button(this.game.world.centerX, this.game.world.centerY + 100, 'JoinGameButton', function () {
            that.getGameCode();
        });*/

        newGame.anchor.setTo(0.5, 0.5);
    //    existingGame.anchor.setTo(0.5, 0.5);
        backgroundImage.anchor.setTo(0.5, 0.5);
    }
};

