/**
 * Created by ross on 2/7/16.
 */
WordsWithBytes.Preload = function(game) {

    this.background = null;
    this.ready = false;
};
WordsWithBytes.Preload.prototype = {

    preload: function () {

        this.splash = this.add.sprite(this.game.world.centerX, this.game.world.centerY, 'WordsWithBytes-logo');
        this.splash.anchor.setTo(0.5);

        this.load.image('_', 'assets/images/bl.png');
        this.load.image('A', 'assets/images/a.png');
        this.load.image('B', 'assets/images/B.png');
        this.load.image('C', 'assets/images/C.png');
        this.load.image('D', 'assets/images/D.png');
        this.load.image('E', 'assets/images/E.png');
        this.load.image('F', 'assets/images/F.png');
        this.load.image('G', 'assets/images/G.png');
        this.load.image('H', 'assets/images/H.png');
        this.load.image('I', 'assets/images/I.png');
        this.load.image('J', 'assets/images/J.png');
        this.load.image('K', 'assets/images/K.png');
        this.load.image('L', 'assets/images/L.png');
        this.load.image('M', 'assets/images/M.png');
        this.load.image('N', 'assets/images/N.png');
        this.load.image('O', 'assets/images/O.png');
        this.load.image('P', 'assets/images/P.png');
        this.load.image('Q', 'assets/images/Q.png');
        this.load.image('R', 'assets/images/R.png');
        this.load.image('S', 'assets/images/S.png');
        this.load.image('T', 'assets/images/T.png');
        this.load.image('U', 'assets/images/U.png');
        this.load.image('V', 'assets/images/V.png');
        this.load.image('W', 'assets/images/W.png');
        this.load.image('X', 'assets/images/X.png');
        this.load.image('Y', 'assets/images/Y.png');
        this.load.image('Z', 'assets/images/Z.png');
        this.load.image('cancel', 'assets/images/cancel.png');
        this.load.image('submitButton', 'assets/images/submit.png');
        this.load.image('startGameButton', 'assets/images/StartGameButton.png');

        this.load.image('_Selected', 'assets/images/LettersForExchange/BlSelected.png');
        this.load.image('ASelected', 'assets/images/LettersForExchange/ASelected.png');
        this.load.image('BSelected', 'assets/images/LettersForExchange/BSelected.png');
        this.load.image('CSelected', 'assets/images/LettersForExchange/CSelected.png');
        this.load.image('DSelected', 'assets/images/LettersForExchange/DSelected.png');
        this.load.image('ESelected', 'assets/images/LettersForExchange/ESelected.png');
        this.load.image('FSelected', 'assets/images/LettersForExchange/FSelected.png');
        this.load.image('GSelected', 'assets/images/LettersForExchange/GSelected.png');
        this.load.image('HSelected', 'assets/images/LettersForExchange/HSelected.png');
        this.load.image('ISelected', 'assets/images/LettersForExchange/ISelected.png');
        this.load.image('JSelected', 'assets/images/LettersForExchange/JSelected.png');
        this.load.image('KSelected', 'assets/images/LettersForExchange/KSelected.png');
        this.load.image('LSelected', 'assets/images/LettersForExchange/LSelected.png');
        this.load.image('MSelected', 'assets/images/LettersForExchange/MSelected.png');
        this.load.image('NSelected', 'assets/images/LettersForExchange/NSelected.png');
        this.load.image('OSelected', 'assets/images/LettersForExchange/OSelected.png');
        this.load.image('PSelected', 'assets/images/LettersForExchange/PSelected.png');
        this.load.image('QSelected', 'assets/images/LettersForExchange/QSelected.png');
        this.load.image('RSelected', 'assets/images/LettersForExchange/RSelected.png');
        this.load.image('SSelected', 'assets/images/LettersForExchange/SSelected.png');
        this.load.image('TSelected', 'assets/images/LettersForExchange/TSelected.png');
        this.load.image('USelected', 'assets/images/LettersForExchange/USelected.png');
        this.load.image('VSelected', 'assets/images/LettersForExchange/VSelected.png');
        this.load.image('WSelected', 'assets/images/LettersForExchange/WSelected.png');
        this.load.image('XSelected', 'assets/images/LettersForExchange/XSelected.png');
        this.load.image('YSelected', 'assets/images/LettersForExchange/YSelected.png');
        this.load.image('ZSelected', 'assets/images/LettersForExchange/ZSelected.png');

      //  this.load.audio('introMusic', ['assets/music/intro-music.mp3']);
        this.load.image('player2Button', 'assets/images/2PlayersButton.png');
        this.load.image('player3Button', 'assets/images/3PlayersButton.png');
        this.load.image('player4Button', 'assets/images/4PlayersButton.png');
        this.load.image('space-background', 'assets/images/space.jpg');
        this.load.image('square', 'assets/images/exchangeLetterSquare.png');

        this.load.image('PassTurnButton', 'assets/images/PassTurn.png');
        this.load.image('PlayWordButton', 'assets/images/PlayWord.png');
        this.load.image('SwapWordsButton', 'assets/images/SwapWords.png');
        this.load.image('QuitGameButton', 'assets/images/QuitGame.png');
        this.load.image('JoinGameButton', 'assets/images/JoinExistingGame.png');
        this.load.image('StartGameButton', 'assets/images/CreateNewGame.png');
        /*this.load.audio('letterDrop', 'assets/music/placeLetter.mp3');
        this.load.audio('letterPlace', 'assets/music/putLetter.mp3');
        */
        //this.load.image('ScoreTemplate', 'assets/images/ScoreImage.png');

        //Load the tile map of the scrabble board using JSON layout.
        this.load.tilemap('ScrabbleBoardTileSet', 'assets/tilemaps/ScrabbleBoardTiles.json', null, Phaser.Tilemap.TILED_JSON);
        this.load.image('tileImage', 'assets/images/ScrabbleBoardTilesetImage.png');
        this.load.image('alphabetImage', 'assets/images/ScrabbleAlphabetOneRowImage.png');
    },

    update: function() {
        if (/*this.cache.isSoundDecoded('introMusic') && */this.ready == false)
        {
            this.ready = true;
            this.state.start('MainMenu');
        }
    }
};