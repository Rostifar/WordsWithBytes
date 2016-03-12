/**
 * Created by ross on 2/24/16.
 */

function Rack(game) {
    this.gameRack = [];
    this.game = game;
    this.positionsOnRackX = [this.calculateRackPositionX()];
    this.positionsOnRackY = [this.calculateRackPositionY()];
}

Rack.prototype.addLetter = function(letter) {
    this.gameRack.push(letter);
};

Rack.prototype = {
    calculateRackPositionX: function() {
    },

    calculateRackPositionY: function() {
    }
}
