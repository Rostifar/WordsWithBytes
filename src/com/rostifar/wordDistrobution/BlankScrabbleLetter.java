package com.rostifar.wordDistrobution;

/**
 * Created by ross on 1/1/16.
 */
public class BlankScrabbleLetter extends ScrabbleLetter {

    public BlankScrabbleLetter(char aLetter) {
        super(aLetter);
    }

    @Override
    public PointValue getPointValue() {
        return new PointValue(0);
    }
}

