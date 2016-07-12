/**
 * Created by Dad and Ross on 5/28/2016.
 */


WordsWithBytes.WaitForPlayers = function(game) {
    this.players = [];
    this.yLocations = [];
};

WordsWithBytes.WaitForPlayers.addPlayers = function(players) {
    var orgPos = game.world.centerY / 1.5;
    var yLocations = [orgPos, orgPos + 90, orgPos + 180, orgPos + 270];

    for (var i = 0; i < players.length; i++) {
        var addPlayer = game.add.text(game.world.centerX, yLocations[i], players[i].name,
            {font: "30px Arial", fill: "#eeeeee", stroke: "#535353", strokeThickness: 15});
        addPlayer.anchor.setTo(0.5);
    }
};


WordsWithBytes.WaitForPlayers.getMessage = function(gameJson) {

    if(gameJson.players.length > WordsWithBytes.WaitForPlayers.players.length) {
        WordsWithBytes.WaitForPlayers.players = gameJson.players;
        WordsWithBytes.WaitForPlayers.addPlayers(gameJson.players);
    }

    if(WordsWithBytes.WaitForPlayers.players.length > 1) {
        this.startGame = game.add.button(game.world.centerX, game.world.centerY * 1.5, 'startGameButton', function() {
            game.state.start("Game");
            let gameState = "Game";
            $.post("/ChangeGameState", {"newGameState":gameState})
                .success(function(data) {
                    WordsWithBytes.subSocket.push("game changed");
                });
        }, this, 2, 1, 0);
        this.startGame.anchor.setTo(0.5);
    }
    WordsWithBytes.WaitForPlayers.players = gameJson.players;
};

WordsWithBytes.WaitForPlayers.prototype = {
    
    create: function() {
        var userName = prompt("Please enter a username ");
        addPlayerToLobby(userName);
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

    }
 };


function addPlayerToLobby(userName) {
    $.post("/AddPlayer", {"username": userName})
        .success(function(data) {
            WordsWithBytes.Player = JSON.parse(data);
            WordsWithBytes.WaitForPlayers.players = [];
            setupSockets();
        })
        .error(function(status) {
            alert("You cannot join this lobby, please try again");
        })
}