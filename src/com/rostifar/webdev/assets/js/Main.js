/**
 * Created by ross on 1/9/16.
 */

var game = new Phaser.Game(1920, 1080, Phaser.AUTO, '', { preload: preload, create: create, update: update()});

function preload(){
    game.load.image()
}

var button;

function create(){
    button = game.add.button(game.world.centerX - 95, 400, 'button', actionOnClick, this, 2, 1, 0);
}

function update(){}

function actionOnClick() {
}

