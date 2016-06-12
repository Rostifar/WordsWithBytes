/**
 * Created by Dad and Ross on 5/28/2016.
 */


WordsWithBytes.WaitForPlayers = function(game) {
    this.players = [];
};

WordsWithBytes.WaitForPlayers.getMessage = function(gameJson) {
    var proto = WordsWithBytes.WaitForPlayers.prototype;
    
    if (gameJson.newPlayerJoined === true) {
         proto.addPlayer(gameJson.newPlayer);   
    }
    
    if (gameJson.gameStateHasChanged === true) {
         proto.startGame();
    }
};

WordsWithBytes.WaitForPlayers.prototype = {
    
    create: function() {
        addPlayerToLobby();
        this.yLocations = [this.game.world.centerY / 1.5, this.game.world.centerY / 1.25, this.game.world.centerY, this.game.world.centerY * 1.25];
        this.scale.fullScreenScaleMode = Phaser.ScaleManager.EXACT_FIT;

        var backgroundImage = this.add.sprite(this.game.world.centerX, this.game.world.centerY, 'space-background');
        backgroundImage.anchor.setTo(0.5, 0.5);

        var bannerFont = "24px Arial";
        var gameSelection = this.game.add.text(this.game.world.centerX, this.game.world.centerY / 5, "Words with Bytes - Your game code is: " + WordsWithBytes.gameCode ,
            {font: bannerFont, fill: "#eeeeee", stroke: "#535353", strokeThickness: 15});
        gameSelection.anchor.setTo(0.5);
        
        var playerList = this.game.add.text(this.game.world.centerX, this.game.world.centerY / 3, "Players in lobby",
        {font: bannerFont, fill: "#eeeeee", stroke: "#535353", strokeThickness: 15});
        playerList.anchor.setTo(0.5);

        this.startGame = this.game.add.button(this.game.world.centerX, this.game.world.centerY * 1.5, 'startGameButton', this.startGame, this, 2, 1, 0);
        this.startGame.anchor.setTo(0.5);
        this.startGame.visible = false;
    },

    addPlayer: function (player) {
        this.players.push(player);
        var addPlayer = this.game.add.text(this.game.world.centerX, this.yLocations[this.players.indexOf(player)], player,
            {font: "30pxArial", fill: "#eeeeee", stroke: "#535353", strokeThickness: 15});
        addPlayer.anchor.setTo(0.5);
    },

    update: function() {

        if (this.players.length > 1) {
            this.startGame.visible = true;
        }
    },
    
    startGame: function() {
        this.game.state.start("Game");
    }
 };


function addPlayerToLobby() {
    var userName = prompt("Please enter a username ");
    $.post("/AddPlayer", {"username": userName})
        .success(function(data) {
        })
        .error(function(status) {
            alert("You cannot join this lobby, please try again")
        })
}