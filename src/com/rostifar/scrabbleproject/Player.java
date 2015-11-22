package com.rostifar.scrabbleproject;

import java.util.List;

/**
 * Created by Dad and Ross on 10/4/2015.
 */
public class Player {
    private Rack rack;
    private String name;
    private int turnCounter;
    private ScrabbleAlphabet scrabbleAlphabet;
    protected static final int RACK_MAX_CAPACITY = 7;


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
        ScrabbleBoard scrabbleBoard = new ScrabbleBoard();
        System.out.println(scrabbleBoard);
    }
    //TODO: Display board to user
    //Implement logic to ask user for input
    //get Input from user
    //based on user response, put letters on board, or implements pass
    //maybe exchange letters
    //get more letters from scrabbleAlphabet
    //put 'em on the rack
    //return

    public void exchangeLetters(){
        return;
    }


    public String toString() {
        return "Player " + getName() + "\n" + getRack();
    }

    public boolean needsLetters() { //if true getNumberOfLettersNeeded

        return rack.getNumberOfLettersOnRack() < 7;
    }

    public int getNumberOfLettersNeeded() {

        int numberOfLettersNeeded = needsLetters() ? RACK_MAX_CAPACITY - rack.getNumberOfLettersOnRack() : 0;

        return numberOfLettersNeeded;
    }

    public void addLetters(List<ScrabbleLetter> letters) {
        rack.addLetters(letters);
    }
}