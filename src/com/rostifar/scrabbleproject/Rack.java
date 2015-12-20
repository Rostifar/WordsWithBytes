package com.rostifar.scrabbleproject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by D14048 on 10/4/2015.
 */
public class Rack {
    protected List<ScrabbleLetter> lettersOnRack;
    private ScrabbleLetter scrabbleLetter;

    public Rack() {
        lettersOnRack = new ArrayList<>();
    }

    public void addLetter(ScrabbleLetter scrabbleLetter) {
        lettersOnRack.add(scrabbleLetter);
    }

    public void addLetters(List<ScrabbleLetter> scrabbleLetters) {
        lettersOnRack.addAll(scrabbleLetters);
    }

    protected boolean isValidLetter(char letterToCheck) {

        return lettersOnRack.contains(scrabbleLetter);
    }

    public void removeLetters() {

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


