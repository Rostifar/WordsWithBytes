/**
 * Created by rostifar on 6/4/16.
 */




WordsWithBytes.setUpSockets = function() {
    var socket = atmosphere;
    var subSocket;
    var request = {
        contentType : "application/json",
        logLevel : 'debug',
        transport : 'websockets' ,
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
        
        try {
            var gameJson = JSON.parse(newMessage);
            manageMessage(gameJson);
        } catch(e) {
            console.log("invalid JSON");
        }
    };

    request.onReconnect = function() {
        //alert("Player returning to game")
    };
    
    request.onError = function(response) {
        alert("It appears our servers may be down, please try again later");
    };
    var subsocket = socket.subscribe(request);
};