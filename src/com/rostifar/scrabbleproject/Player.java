package com.rostifar.scrabbleproject;

import com.rostifar.wordDistribution.ScrabbleLetter;
import com.rostifar.wordDistribution.ScrabbleWord;
import com.rostifar.gamecontrol.ScoreKeeper;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Dad and Ross on 10/4/2015.
 */
public class Player implements Serializable {
    private Rack rack;
    private String name;
    private ScoreKeeper scoreKeeper;
    protected static final int RACK_MAX_CAPACITY = 7;


    public Player(String aName) {
        name = aName;
        rack = new Rack();
        scoreKeeper = new ScoreKeeper();
    }

    public String getName() {
        return name;
    }

    public boolean hasRack(){
        return rack != null;
    }

    public int getCurrentPlayerScore(){
        return scoreKeeper.getplayerScore();
    }

    public ScoreKeeper getScoreKeeper(){
        return scoreKeeper;
    }


    public Rack getRack() {
        return rack;
    }

    public boolean isValidWord(ScrabbleWord scrabbleWord) {
        return rack.isValidWord(scrabbleWord);
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

    public void getLettersToExchange(char[] lettersToExchange) {
        rack.exchangeLetters(lettersToExchange);
    }

    public void addLetters(List<ScrabbleLetter> letters) {
        rack.addLetters(letters);
    }

    public void removeLetters(ScrabbleWord scrabbleWord) {rack.removeLetters(scrabbleWord);}
}