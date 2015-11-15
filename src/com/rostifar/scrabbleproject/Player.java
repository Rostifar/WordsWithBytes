package com.rostifar.scrabbleproject;

/**
 * Created by Dad and Ross on 10/4/2015.
 */
public class Player {
    private Rack rack;
    private String name;
    private int turnCounter;
    private ScrabbleAlphabet scrabbleAlphabet;

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

    public void takeTurn() {return;}
        //TODO: Display board to user
        //Implement logic to ask user for input
        //get Input from user
        //based on user response, put letters on board, or implements pass
        //maybe exchange letters
        //get more letters from scrabbleAlphabet
        //put 'em on the rack
        //return

    public void exchangeLetters(){return;}


    public String toString() {
        return "Player " + getName() + " - " + getRack();
    }

}
