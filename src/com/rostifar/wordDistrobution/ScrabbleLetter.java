package com.rostifar.wordDistrobution;

/**
 * Created by Cal Briden (Dad) & Ross Briden on 8/12/2015.
 * Model class for representation of an individual letter that can appear in the scrabble alphabet.
 * see  https://en.wikipedia.org/wiki/Scrabble_letter_distributions
 */
public class ScrabbleLetter {
    protected char letter;
    protected PointValue pointValue;

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
    public boolean equals(Object letter) {

        return this.letter == ((ScrabbleLetter) letter).getLetter();
    }



    @Override
    public String toString() {
        return "ScrabbleLetter: " + letter + "\tPoint value: " + pointValue + "\t";
    }




}
