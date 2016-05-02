package com.rostifar.scrabbleproject;

import com.rostifar.wordDistribution.ScrabbleAlphabet;
import com.rostifar.wordDistribution.ScrabbleLetter;
import com.rostifar.wordDistribution.ScrabbleWord;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by D14048 on 10/4/2015.
 */
public class Rack implements Serializable {
    private List<ScrabbleLetter> lettersOnRack;
    private List<Character> letterCharacters;

    public Rack() {
        lettersOnRack = new ArrayList<>();
    }

    public List<ScrabbleLetter> getLettersOnRack() {
        return lettersOnRack;
    }

    public List<Character> getCharactersOnRack() {
        letterCharacters = new ArrayList<>();

        for (ScrabbleLetter letter : lettersOnRack) {
            letterCharacters.add(letter.getLetter());
        }
        return letterCharacters;
    }

    public void addLetters(List<ScrabbleLetter> scrabbleLetters) {
        lettersOnRack.addAll(scrabbleLetters);
    }

    public void exchangeLetters(char[] lettersToExchange) {
        ScrabbleLetter scrabbleLetter;

        for (char letter : lettersToExchange) {
            scrabbleLetter = new ScrabbleLetter(letter);
            lettersOnRack.remove(scrabbleLetter);
            ScrabbleAlphabet.getInstance().getExchangedLetters(scrabbleLetter);
        }
    }

    protected boolean isValidWord(ScrabbleWord scrabbleWordToCheck) {

        for (int i = 0; i < scrabbleWordToCheck.getNumberOfLetters(); i++) {
            if (!lettersOnRack.contains(scrabbleWordToCheck.getLetterAt(i))) {
                return false;
            }
        }
        return true;
    }

    public void replaceBlankLetter(ScrabbleLetter replacementLetter, ScrabbleLetter blankLetter) {
        lettersOnRack.add(replacementLetter);
        lettersOnRack.remove(blankLetter);
    }

    public void removeLetters(ScrabbleWord scrabbleWord) {

        for (ScrabbleLetter letter : scrabbleWord.lettersInWord()) {

            lettersOnRack.remove(letter);
        }
    }


    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        if (lettersOnRack.isEmpty()) {

            return "|-------|";
        }

        stringBuilder.append("| ");

        for (ScrabbleLetter letter : lettersOnRack) {
            stringBuilder.append(letter.getLetter()).append("  |  ");
        }
        stringBuilder.append("\n");

        stringBuilder.append("| ");

        for (ScrabbleLetter letter : lettersOnRack) {
            stringBuilder.append(letter.getPointValue().getValue()).append("  |  ");
        }

        return stringBuilder.toString();

    }

    protected int getNumberOfLettersOnRack() {
        return lettersOnRack.size();
    }

}


