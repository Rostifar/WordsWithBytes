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
    var playerList = JSON.parse(players);
    console.log(playerList);

    for (var i = 0; i < playerList.length; i++) {
        var addPlayer = game.add.text(game.world.centerX, yLocations[i], playerList[i].name,
            {font: "30px Arial", fill: "#eeeeee", stroke: "#535353", strokeThickness: 15});
        addPlayer.anchor.setTo(0.5);
    }
    return playerList;
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
            WordsWithBytes.WaitForPlayers.players = WordsWithBytes.WaitForPlayers.addPlayers(data);
            console.log(WordsWithBytes.WaitForPlayers.players.length)
        })
        .error(function(status) {
            alert("You cannot join this lobby, please try again");
        })
}