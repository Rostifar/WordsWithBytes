package com.rostifar.scrabbleproject;

import java.util.Map;

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
       // pointValue = new PointValue(aPointValue);

    }

    @Override
    public String toString() {
        return "ScrabbleLetter: " + letter + "\tPoint value: " + pointValue + "\t";
    }

    private int getPointValue(char aLetter) {
        //Switch statement
        //case a, b,c ,d return point value for these letters
        return getPointValue(aLetter);
    }


}
