package com.rostifar.scrabbleproject;

/**
 * Created by Cal Briden (Dad) & Ross Briden on 8/12/2015.
 * Model class for representation of an individual letter that can appear in the scrabble alphabet.
 * see  https://en.wikipedia.org/wiki/Scrabble_letter_distributions
 */
public class ScrabbleLetter {
    private char letter;
    private PointValue pointValue;

    public ScrabbleLetter(char aLetter) {
        letter = aLetter;
        pointValue = new PointValue(aLetter);

    }

    public char getLetter () {
        return letter;
    }

    public PointValue getPointValue () {
        return pointValue;
    }

    @Override
    public String toString() {
        return "ScrabbleLetter: " + letter + "\tPoint value: " + pointValue + "\t";
    }




}
