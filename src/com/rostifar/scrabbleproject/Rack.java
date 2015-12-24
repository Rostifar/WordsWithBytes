package com.rostifar.scrabbleproject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by D14048 on 10/4/2015.
 */
public class Rack {
    protected List<ScrabbleLetter> lettersOnRack;
    private ScrabbleLetter scrabbleLetter;
    private ScrabbleAlphabetImpl scrabbleAlphabet = new ScrabbleAlphabetImpl();

    public Rack() {
        lettersOnRack = new ArrayList<>();
    }

    public void addLetter(ScrabbleLetter scrabbleLetter) {
        lettersOnRack.add(scrabbleLetter);
    }

    public void addLetters(List<ScrabbleLetter> scrabbleLetters) {
        lettersOnRack.addAll(scrabbleLetters);
    }

    public void exchangeLetters(char[] lettersToExchange) {
        List<ScrabbleLetter> letterList;

        for (char letter : lettersToExchange) {
            scrabbleLetter = new ScrabbleLetter(letter);
            letterList = new ArrayList<>();
            letterList.add(scrabbleLetter);
            lettersOnRack.remove(scrabbleLetter);
            scrabbleAlphabet.getExchangedLetters(letterList);
        }
    }

    protected boolean isValidWord(ScrabbleWord scrabbleWordToCheck) {

        for (int i =0; i < scrabbleWordToCheck.getNumberOfLetters(); i++) {
            if (!lettersOnRack.contains(scrabbleWordToCheck.getLetterAt(i))) {
                return false;
            }
        }
        return true;
    }


    public void removeLetters(ScrabbleWord scrabbleWord) {
        List<ScrabbleLetter> lettersToRemove = new ArrayList<>();

        for (int i = 0; i < scrabbleWord.getNumberOfLetters(); i++) {
            lettersToRemove.add(lettersOnRack.get(lettersOnRack.indexOf(scrabbleWord.getLetterAt(i))));
        }
        lettersOnRack.removeAll(lettersToRemove);
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


