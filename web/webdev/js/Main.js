/**
 * Created by ross on 3/25/16.
 */

var gameHeight = 800;
var gameWidth = 900;

var scrabbleGame = (function() {
    var game = new Phaser.Game(gameWidth, gameHeight, Phaser.AUTO, 'WordsWithBytes');
    var boot = game.state.add('Boot', WordsWithBytes.Boot);
    var preload = game.state.add('Preload', WordsWithBytes.Preload);
    var login = game.state.add('Login', WordsWithBytes.Login);
    var mainMenu = game.state.add('MainMenu', WordsWithBytes.MainMenu);
    var gameInterface = game.state.add('Game', WordsWithBytes.Game);
    game.state.start('Boot');

    return {
        getGame: game,
        bootKey: boot,
        preloadKey: preload,
        loginKey: login,
        mainMenuKey: mainMenu,
        gameInterfaceKey: gameInterface
    }
})();

