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

     //   this.load.image('bl', 'assets/images/bl.png');
        this.load.image('_', 'assets/images/a.png'); //Blank letter
        this.load.image('A', 'assets/images/A.png');
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

        this.load.audio('introMusic', ['assets/music/intro-music.mp3']);
        this.load.image('scrabbleBoard', 'assets/images/ScrabbleBoard5.png');
        this.load.image('player2Button', 'assets/images/2PlayersButton.png');
        this.load.image('player3Button', 'assets/images/3PlayersButton.png');
        this.load.image('player4Button', 'assets/images/4PlayersButton.png');
        this.load.image('scrabbleSample', 'assets/images/SampleScrabbleImage.jpg');
        this.load.image('space-background', 'assets/images/space.jpg');

        this.load.image('PassTurnButton', 'assets/images/PassTurn.png');
        this.load.image('PlayWordButton', 'assets/images/PlayWord.png');
        this.load.image('SwapWordsButton', 'assets/images/SwapWords.png');
        this.load.image('QuitGameButton', 'assets/images/QuitGame.png');
        this.load.image('ScoreTemplate', 'assets/images/ScoreImage.png');

        //Load the tile map of the scrabble board using JSON layout.
        this.load.tilemap('ScrabbleBoardTileSet', 'assets/tilemaps/ScrabbleBoardTiles.json', null, Phaser.Tilemap.TILED_JSON);
        this.load.image('tileImage', 'assets/images/ScrabbleBoardTilesetImage.png');
        this.load.image('alphabetImage', 'assets/images/ScrabbleAlphabetOneRowImage.png');
    },

    update: function() {
        if (this.cache.isSoundDecoded('introMusic') && this.ready == false)
        {
            this.ready = true;
            this.state.start('MainMenu');
        }
    }
};