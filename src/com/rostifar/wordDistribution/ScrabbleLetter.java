package com.rostifar.wordDistribution;

import java.io.Serializable;

/**
 * Created by Cal Briden (Dad) & Ross Briden on 8/12/2015.
 * Model class for representation of an individual letter that can appear in the scrabble alphabet.
 * see  https://en.wikipedia.org/wiki/Scrabble_letter_distributions
 */
public class ScrabbleLetter implements Serializable{
    protected char letter;
    private PointValue pointValue;
    private int desiredPositionCol;
    private int desiredPositionRow;

    /**
     * @ScrabbleLetter[constructor]
     * purpose-> converts a provided character into a ScrabbleLetter object when instantiated
     * */
    public ScrabbleLetter(char aLetter) {
        letter = aLetter;
        pointValue = new PointValue(aLetter);
    }

    /**
     * @getLetter
     * purpose-> returns the character value for a ScrabbleLetter
     * */
    public char getLetter() {
        return letter;
    }

    /**
     * @setLetter
     * purpose-> allows for the selection of a new character value for a ScrabbleLetter object
     * */
    public void setLetter(char newLetter) {
        letter = newLetter;
    }

    /**
     * @getPointValue
     * purpose-> returns the point value for a given ScrabbleLetter
     * */
    public PointValue getPointValue () {
        return pointValue;
    }

    /**
     * @setNewPointValue
     * purpose-> creates a new point value for a selected ScrabbleLetter*/
    public void setNewPointValue(int newValue) {
        pointValue = new PointValue(newValue);
    }

    /**
     * @setDesiredPositionCol
     * purpose-> sets the column position for a selected ScrabbleLetter object
     * */
    public void setDesiredPositionCol(int col) {
        this.desiredPositionCol = col;
    }

    /**
     * @setDesiredPositionRow
     * purpose-> sets the row position for a selected ScrabbleLetter object
     * */
    public void setDesiredPositionRow(int row) {
        this.desiredPositionRow = row;
    }

    /**
     * @getDesiredPositionCol
     * purpose-> returns the column value for a selected ScrabbleLetter object
     * */
    public int getDesiredPositionCol() {
        return desiredPositionCol;
    }

    /**
     * @getDesiredPositionRow
     * purpose-> returns the row value for a selected ScrabbleLetter object
     * */
    public int getDesiredPositionRow() {
        return desiredPositionRow;
    }

    @Override
    public boolean equals(Object letter) {

        return this.letter == ((ScrabbleLetter) letter).getLetter();
    }

    @Override
    public String toString() {
        return "ScrabbleLetter: " + letter + "\tPoint value: " + pointValue + "\t";
    }

}
