package com.rostifar.scrabbleproject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by D14048 on 10/4/2015.
 */
public class Rack {
    protected List<ScrabbleLetter> lettersOnRack;
    public static final int ROW_LENGTH = 7;
    public static final int COLUMN_LENGTH = 1;

    public Rack() {
        lettersOnRack = new ArrayList<>();
    }

    public void addLetter(ScrabbleLetter scrabbleLetter) {
        lettersOnRack.add(scrabbleLetter);
    }

    public void addLetters(List<ScrabbleLetter> scrabbleLetters) {
        lettersOnRack.addAll(scrabbleLetters);
    }

    public List getLettersCurrentlyOnRack() {
        return lettersOnRack;
    }

    protected void removeLetter() {

        lettersOnRack.size();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        if (lettersOnRack.isEmpty()) {

            return "|-------|";
        }

        stringBuilder.append("| ");

        for (ScrabbleLetter letter : lettersOnRack) {
            stringBuilder.append(letter.getLetter()) .append("  |  ");
        }
        stringBuilder.append("\n");

        stringBuilder.append("| ");

        for (ScrabbleLetter letter : lettersOnRack) {
            stringBuilder.append(letter.getPointValue().getValue()) .append("  |  ");
        }

        return stringBuilder.toString();

    }

    protected int getNumberOfLettersOnRack() {
        return lettersOnRack.size();
    }
}


