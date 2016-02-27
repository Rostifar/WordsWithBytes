package com.rostifar.wordDistrobution;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ross on 12/19/15.
 */
public class ScrabbleWord {
    private List<ScrabbleLetter> word;
    private String wordAsString;
    List<ScrabbleLetter> blankLetters = new ArrayList<>();

    public ScrabbleWord(String rawWord) {
        word = new ArrayList<>();
        wordAsString = rawWord;
        parseWord(rawWord.toUpperCase());

    }

    public ScrabbleLetter getLetterAt(int index) {
        return word.get(index);
    }

    public int getNumberOfLetters() {
        return word.size();
    }

    /**
    *Takes apart userinputed word and converts each Character to a ScrabbleLetter, In order to validate the user actually has these letters in their Rack.
    *Places each instance of a ScrabbleLetter into an array list
    */
    private void parseWord(String rawWord) {
        char[] lettersUsed = rawWord.toCharArray();

        for (char letter : lettersUsed) {
            word.add(new ScrabbleLetter(letter));
        }
    }

    public void searchForBlankLetter() {

        for (ScrabbleLetter scrabbleLetter : word) {

            if (scrabbleLetter.letter == '_') {
                blankLetters.add(scrabbleLetter);
            }
        }
    }

    public ScrabbleLetter getBlankLetter(int currentBlankLetterIndex) {
        return blankLetters.get(currentBlankLetterIndex);
    }

    public int getNumberOfBlankLetters() {
        searchForBlankLetter();
        return blankLetters.size();
    }

    @Override
    public String toString() {
        return wordAsString;
    }

    public void replaceLetter(ScrabbleLetter letterToReplace, int currentBlankLetter) {
        word.add(word.indexOf(blankLetters.get(currentBlankLetter)), letterToReplace);
        word.remove(blankLetters.get(currentBlankLetter));
    }

    public void clearFoundBlankLetters() {
        blankLetters.clear();
    }
}
