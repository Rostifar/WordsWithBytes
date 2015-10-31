package com.rostifar.scrabbleproject;

/**
 * Created by D14048 on 10/4/2015.
 */
public class Player {
    private Rack rack;
    private String name;
    private int turnCounter;
    private ScrabbleAlphabetImpl scrabbleAlphabet;

    public Player(String aName) {
        name = aName;
        turnCounter = 0;
        rack = new Rack();

    }

    public String getName() {
        return name;
    }

    public boolean hasRack(){
        return rack != null;
    }

    public Rack getRack() {
        return rack;
    }

    public boolean isFirstTurn() {
        return turnCounter == 0;
    }

    public void takeTurn() {

    }

    public String toString() {
        return "Player " + getName() + " - " + getRack();
    }

}
