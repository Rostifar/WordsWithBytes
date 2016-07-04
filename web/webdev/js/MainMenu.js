/**
 * Created by ross on 1/31/16.
 */
var setupSockets = (function () {
    var socket = atmosphere;
    var subSocket;
    var request = {
        url: "/WordsWithBytes",
        logLevel : 'debug',
        contentType : "application/json",
        transport : 'streaming' ,
        trackMessageLength : true,
        reconnectInterval : 5000 };

    function manageMessage(gameJson) {

        if(game.state.key() === "WaitForPlayers") {
            WordsWithBytes.WaitForPlayers.getMessage(gameJson);
        }

        if (game.state.key() === "Game") {
            WordsWithBytes.Game.getMessage(gameJson);
        }
    }

    request.onOpen = function(response) {
        alert("connected to game lobby");
    };

    request.onMessage = function (response) {
        var newMessage = response.responseBody;
        alert("new message");
        console.log(newMessage);
        try {
            var gameJson = JSON.parse(newMessage);
            console.log(gameJson);
        } catch(e) {
            console.log("invalid JSON");
        }
    };

    request.onReconnect = function() {
        //alert("Player returning to game")
    };

    request.onError = function(response) {
        alert("onError: Server problem: " + response.toString());
    };
    var subsocket = socket.subscribe(request);
});

WordsWithBytes.MainMenu = function(game) {
};

var newGameButton;
var joinGameButton;
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

        newGameButton = this.game.add.button(this.game.world.centerX, this.game.world.centerY / 2, 'StartGameButton', startNewGameOnClick, this, 2, 1, 0);
        newGameButton.anchor.setTo(0.5, 0.5);

        joinGameButton = this.game.add.button(this.game.world.centerX, this.game.world.centerY, 'JoinGameButton', joinExistingGameOnClick, this, 2, 1, 0);
        joinGameButton.anchor.setTo(0.5, 0.5);

      /*  submitButton = this.game.add.button(this.game.world.width / 3 + 150, 490, 'submitButton', joinExistingGameOnClick, this, 2, 1, 0);
        submitButton.visible = false;*/

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
            game.state.start("WaitForPlayers"); //, false, false, data);
        })
        .error(function (status) {
            console.log("Error occurred when attempting to start a new Game:" + status);
            //TODO: add proper error handling
        })
}

function joinExistingGameOnClick() {

    /*theGameID.visible = true;
    submitButton.visible = true;*/
    var gameCode = prompt("Please enter the Game ID: ");

    $.post("/JoinExistingGame", {"gameCode": gameCode})
        .success(function (data) {
            if (data !== "Error, Game doesn't exist" && data !== "Error, Game lobby is full") {
                console.log("Game Resumed - Game Code: " + data + "\nStatus: " + status);
                WordsWithBytes.gameCode = gameCode;
                setupSockets();
                game.state.start("WaitForPlayers"); //, false, false, data);
            } else {
                alert("the game you have selected does not exist or is full, please try again")
            }
        })
        .error(function (status) {
            alert("the game you have selected does no exist, please try again");
            //TODO: add proper error handling
        })
}


