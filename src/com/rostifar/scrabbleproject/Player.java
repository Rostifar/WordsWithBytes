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
        return needsLetters() ? RACK_MAX_CAPACITY - rack.getNumberOfLettersOnRack() : 0;
    }

    public void addLetters(List<ScrabbleLetter> letters) {
        rack.addLetters(letters);
    }
}