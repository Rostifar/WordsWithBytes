package com.rostifar.scrabbleproject;

/**
 * Created by Dad on 10/4/2015.
 */
public class PointValue {

    public int value;

    public PointValue(char aLetter) {
        value = getValueForLetter(aLetter);
    }

    @Override
    public String toString() {
        return "PointValue: " + value;
    }

    private int getValueForLetter(char aLetter) {
        return 0;
    }
}