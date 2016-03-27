/**
 * Created by ross on 2/7/16.
 */
WordsWithBytes.Preload = function (game) {

    this.background = null;
    this.ready = false;
};
WordsWithBytes.Preload.prototype = {

    preload: function () {

        this.splash = this.add.sprite(this.game.world.centerX, this.game.world.centerY, 'WordsWithBytes-logo');
        this.splash.anchor.setTo(0.5);

        this.load.image('bl', 'assets/images/bl.png');
        this.load.image('a', 'assets/images/a.png');
        this.load.image('bLetter', 'assets/images/LetterB.png');
        this.load.image('cLetter', 'assets/images/LetterC.png');
        this.load.image('dLetter', 'assets/images/LetterD.png');
        this.load.image('eLetter', 'assets/images/LetterE.png');
        this.load.image('fLetter', 'assets/images/LetterF.png');
        this.load.image('gLetter', 'assets/images/LetterG.png');
        this.load.image('hLetter', 'assets/images/LetterH.png');
        this.load.image('iLetter', 'assets/images/LetterI.png');
        this.load.image('jLetter', 'assets/images/LetterJ.png');
        this.load.image('kLetter', 'assets/images/LetterK.png');
        this.load.image('lLetter', 'assets/images/LetterL.png');
        this.load.image('mLetter', 'assets/images/LetterM.png');
        this.load.image('nLetter', 'assets/images/LetterN.png');
        this.load.image('oLetter', 'assets/images/LetterO.png');
        this.load.image('pLetter', 'assets/images/LetterP.png');
        this.load.image('qLetter', 'assets/images/LetterQ.png');
        this.load.image('rLetter', 'assets/images/LetterR.png');
        this.load.image('sLetter', 'assets/images/LetterS.png');
        this.load.image('tLetter', 'assets/images/LetterT.png');
        this.load.image('uLetter', 'assets/images/LetterU.png');
        this.load.image('vLetter', 'assets/images/LetterV.png');
        this.load.image('wLetter', 'assets/images/LetterW.png');
        this.load.image('xLetter', 'assets/images/LetterX.png');
        this.load.image('yLetter', 'assets/images/LetterY.png');
        this.load.image('zLetter', 'assets/images/LetterZ.png');
        this.load.audio('introMusic', ['assets/music/intro-music.mp3']);
        this.load.image('scrabbleBoard', 'assets/images/ScrabbleBoard5.png');
        this.load.image('player2Button', 'assets/images/2PlayersButton.png');
        this.load.image('player3Button', 'assets/images/3PlayersButton.png');
        this.load.image('player4Button', 'assets/images/4PlayersButton.png');
        this.load.image('scrabbleSample', 'assets/images/SampleScrabbleImage.jpg');
        this.load.image('space-background', 'assets/images/space.jpg');
    },

    update: function () {
        if (this.cache.isSoundDecoded('introMusic') && this.ready == false) {
            this.ready = true;
            this.state.start('Game');
        }
    }
};

//# sourceMappingURL=Preload-compiled.js.map