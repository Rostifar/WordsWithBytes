package com.rostifar.wordDistribution;

import java.io.Serializable;

/**
 * Created by Cal Briden (Dad) & Ross Briden on 8/12/2015.
 * Model class for representation of an individual letter that can appear in the scrabble alphabet.
 * see  https://en.wikipedia.org/wiki/Scrabble_letter_distributions
 */
public class ScrabbleLetter implements Serializable{
    protected char letter;
    protected PointValue pointValue;
    protected int desiredPositionCol;
    protected int desiredPositionRow;

    public ScrabbleLetter(char aLetter) {
        letter = aLetter;
        pointValue = new PointValue(aLetter);
    }

    public char getLetter () {
        return letter;
    }

    public void setLetter(char newLetter) {
        letter = newLetter;
    }

    public PointValue getPointValue () {
        return pointValue;
    }

    @Override
    public boolean equals(Object letter) {

        return this.letter == ((ScrabbleLetter) letter).getLetter();
    }

    @Override
    public String toString() {
        return "ScrabbleLetter: " + letter + "\tPoint value: " + pointValue + "\t";
    }

    public void setDesiredPositionCol(int col) {
        this.desiredPositionCol = col;
    }

    public void setDesiredPositionRow(int row) {
        this.desiredPositionRow = row;
    }
    public int getDesiredPositionCol() {
        return desiredPositionCol;
    }
    public int getDesiredPositionRow() {
        return desiredPositionRow;
    }

}
