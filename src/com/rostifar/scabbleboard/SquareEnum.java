package com.rostifar.scabbleboard;

import java.io.Serializable;

/**
 * Created by DAD on 10/18/2015.
 */
public enum SquareEnum implements Serializable {
    REGULAR("  "), DOUBLE_LETTER("DL"), TRIPLE_LETTER("TL"), DOUBLE_WORD("DW"), TRIPLE_WORD("TW"), CENTER_STAR(" *");

    private String displayValue;

    SquareEnum(String aDisplayValue) {
        displayValue = aDisplayValue;
    }

    @Override
    public String toString() {
        return this.displayValue;
    }
}
