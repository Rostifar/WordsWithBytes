WordsWithBytes.ManageMessages = function(gameJson) {
    
    let gameState = gameJson.gameState;

    if (gameState === undefined) {
        updateWaitForPlayers();
    } else if (gameState === "Game") {
        updateGame();
    }

    function updateWaitForPlayers() {

        if(gameJson.players.length > WordsWithBytes.WaitForPlayers.players.length) {
            WordsWithBytes.WaitForPlayers.players = gameJson.players;
            WordsWithBytes.WaitForPlayers.addPlayers(gameJson.players);
        }

        if(WordsWithBytes.WaitForPlayers.players.length > 1) {
            let gameState = "Game";
            let startGame = game.add.button(game.world.centerX, game.world.centerY * 1.5, 'startGameButton', function() {

                game.state.start("Game");
                $.post("/ChangeGameState", {"newGameState":gameState})
                    .success(function(data) {
                        startGame.destroy();
                        WordsWithBytes.subSocket.push("game changed");
                    });
            }, this, 2, 1, 0);
            startGame.anchor.setTo(0.5);
        }
        WordsWithBytes.WaitForPlayers.players = gameJson.players;
    }

    function updateGame() {
        let player = WordsWithBytes.Player;
        let wg = WordsWithBytes.Game;
        let proto = WordsWithBytes.Game.prototype;
        let isInGameSession = game.state.current === gameJson.gameState;

        if (!isInGameSession) {
            game.state.start("Game");
        } else {
            updateConstructs();
            if (player === gameJson.currentPlayer) {
                alert("its your turn! Please select a move.");
                proto.activateButtons();
            } else {
                alert("please wait until the other players have moved.");
                proto.deactivateButtons();
            }
        }

        function updateConstructs() {
            let playerRack = player.rack.lettersOnRack;
            let jsonRack = gameJson.players[player.indx].rack.lettersOnRack;

            proto.initScrabbleRack(jsonRack);

            playerRack.length = 0;
            playerRack = jsonRack;
        }
    }
};
