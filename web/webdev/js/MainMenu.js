/**
 * Created by ross on 1/31/16.
 */

WordsWithBytes.MainMenu = function(game) {
};

var newGameButton;
var resumeGameButton;
var theGameID;
var submitButton;

WordsWithBytes.MainMenu.prototype = {

    create: function() {

        var backgroundImage = this.add.sprite(this.game.world.centerX, this.game.world.centerY, 'space-background');
        backgroundImage.anchor.setTo(0.5, 0.5);

        this.scale.fullScreenScaleMode = Phaser.ScaleManager.EXACT_FIT;

        var bannerFont = "24px Arial";
        var gameSelection = this.game.add.text(this.game.world.centerX, this.game.world.centerY / 6, "Words with Bytes - A Virtual Scrabble Experience", {font: bannerFont, fill: "#eeeeee", stroke: "#535353", strokeThickness: 15});
        gameSelection.anchor.set(0.5);

        newGameButton = this.game.add.button(this.game.world.centerX, this.game.world.centerY / 2, 'startNewGameButton', startNewGameOnClick, this, 2, 1, 0);
        newGameButton.anchor.setTo(0.5, 0.5);

        resumeGameButton = this.game.add.button(this.game.world.centerX, this.game.world.centerY, 'joinExistingGameButton', resumeGameOnClick, this, 2, 1, 0);
        resumeGameButton.anchor.setTo(0.5, 0.5);

        submitButton = this.game.add.button(this.game.world.width / 3 + 150, 490, 'submitButton', joinExistingGameOnClick, this, 2, 1, 0);
        submitButton.visible = false;

        theGameID = game.add.inputField(this.game.world.width / 3, 500, {
            font: '18px Arial',
            width: 100,
            max: 9999,
            min: 0,
            padding: 4,
            borderWidth: 1,
            type: Fabrique.InputType.number,
            borderRadius: 6,
            placeHolder: 'Game ID',
            textAlign: 'left',
            zoom: false
        });
        theGameID.visible = false;
        //theGameID.visibility
    }
};

function startNewGameOnClick() {
    var that = this;

    $.post("/StartNewGame")
        .success(function (data) {
            console.log("Game Started - Game Code: " + data + "\nStatus: " + status);
            var gameCodeDisplay = that.game.add.text(game.world.centerX, game.world.centerY / 3, "Your game code is:" + data, {font: "24px Arial", fill: "#eeeeee", stroke: "#535353", strokeThickness: 15});
            WordsWithBytes.gameCode = data;
            gameCodeDisplay.anchor.set(0.5, 0.5);
            addPlayerToLobby();
        })
        .error(function (status) {
            console.log("Error occurred when attempting to start a new Game:" + status);
            //TODO: add proper error handling
        })
}

function resumeGameOnClick() {
    theGameID.visible = true;
    submitButton.visible = true;
}

function joinExistingGameOnClick() {
    $.post("/JoinExistingGame", {"gameCode": theGameID.value})
        .success(function (data) {
            if (data !== "Error, Game doesn't exist" && data !== "Error, Game lobby is full") {
                console.log("Game Resumed - Game Code: " + data + "\nStatus: " + status);
                addPlayerToLobby();
            } else {    
                alert("the game you have selected does not exist or is full, please try again")
            }
        })
        .error(function (status) {
            alert("the game you have selected does no exist, please try again");
            //TODO: add proper error handling
        })
}

function addPlayerToLobby() {
    var userName = prompt("Please enter a username");
    $.post("/AddPlayer", {"username": userName})
        .success(function(data) {
            game.state.start("WaitForPlayers"); //, false, false, data);
        })
        .error(function(status) {
            alert("You cannot join this lobby, please try again")
        })
}

