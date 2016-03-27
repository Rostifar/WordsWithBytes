/**
 * Created by ross on 3/25/16.
 */

var gameHeight = 800;
var gameWidth = 900;

var game = new Phaser.Game(gameWidth, gameHeight, Phaser.AUTO, 'WordsWithBytes');

game.state.add('Boot', WordsWithBytes.Boot);
game.state.add('Preload', WordsWithBytes.Preload);
game.state.add('Login', WordsWithBytes.Login);
game.state.add('MainMenu', WordsWithBytes.MainMenu);
game.state.add('Game', WordsWithBytes.Game);
game.state.start('Boot');