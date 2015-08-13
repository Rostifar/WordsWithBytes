package com.rostifar.scrabbleproject;

/**
 * Created by Cal Briden (Dad) on 8/12/2015.
 * Model class for representation of an individual letter that can appear in the scrabble alphabet.
 * see  https://en.wikipedia.org/wiki/Scrabble_letter_distributions
 */
public class ScrabbleLetter {
    private char letter;
    private int pointValue = 0;

    public ScrabbleLetter(char letter) {
        this.letter = letter;
        pointValue = findPointValueForLetter();
    }

    //Prevent use of default constructor
    private ScrabbleLetter() {}

    /**
     * @return true is the letter is a vowel
     */
    public boolean isVowel() {
        return "AEIOU".indexOf(Character.toUpperCase(letter)) > 0;
    }

    /**
     * @return the point value of the letter. Will be between 1 and 10;
     */
    public int getPointValue() {
        return pointValue;
    }

    /**
     * lookup the point value (from 1 - 10) for the specified letter based on the scrabble alphabet map.
     * @return the point value (from 1 - 10)
     */
    private int findPointValueForLetter() {
        return 1;
    }
}
