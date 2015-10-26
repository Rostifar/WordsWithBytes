package com.rostifar.scrabbleproject;

/**
 * Created by Dad on 10/4/2015.
 */
public class PointValue {

    public int value;

    public PointValue(int aValue) {
        value = aValue;
    }

    @Override
    public String toString() {
        return "PointValue: " + value;
    }
}
