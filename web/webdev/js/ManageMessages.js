WordsWithBytes.ManageMessages = function(gameJson) {
    
    let gameState = gameJson.gameState;

    if (gameState === undefined) {
        updateWaitForPlayers();
    } else if (gameState === "Game") {
        updateGame();
    }

    function updateWaitForPlayers() {
        let proto = WordsWithBytes.WaitForPlayers.prototype;

        if(gameJson.players.length > WordsWithBytes.WaitForPlayers.players.length) {
            WordsWithBytes.WaitForPlayers.players = gameJson.players;
            WordsWithBytes.WaitForPlayers.addPlayers(gameJson.players);
        }

        if(WordsWithBytes.WaitForPlayers.players.length > 1) {
            proto.makeButtonVisible();
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
            console.log(player);
            if (player.indx === gameJson.currentPlayer.indx) {
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
            let wg = WordsWithBytes.Game;
            let jsonScore = gameJson.players[player.indx].scoreKeeper.totalPoints;

            function removePreviousLetters() {
                for (let j = 0; j < wg.lettersOnRack.length; j++) {
                    wg.lettersOnRack[j].destroy();
                }
                wg.lettersOnRack.length = 0;
                playerRack.length = 0;
            }

            if (WordsWithBytes.length !== 0) {
                removePreviousLetters();
                proto.initScrabbleRack(jsonRack);
            } else {
                proto.initScrabbleRack(jsonRack);
            }
            playerRack = jsonRack;
            proto.updatePlayerScores(jsonScore);
        }
    }
};
