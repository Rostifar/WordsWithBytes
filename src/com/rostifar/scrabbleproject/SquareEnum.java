package com.rostifar.scrabbleproject;

/**
 * Created by DAD on 10/18/2015.
 */
public enum SquareEnum {
    REGULAR("  "), DOUBLE_LETTER("DL"), TRIPLE_LETTER("TL"), DOUBLE_WORD("DW"), TRIPLE_WORD("TW"), CENTER_STAR(" *");

    private String displayValue;

    public SquareEnum(String aDisplayValue) {
        displayValue = aDisplayValue;
    }


    @Override
    public String toString() {
        return this.displayValue;
    }
}
