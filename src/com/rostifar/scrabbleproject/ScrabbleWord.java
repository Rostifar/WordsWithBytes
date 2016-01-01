package com.rostifar.scrabbleproject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ross on 12/19/15.
 */
public class ScrabbleWord {
    private List<ScrabbleLetter> word;
    private String wordAsString;

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

    public boolean wordContainsBlankLetter() {
        List<Boolean> searchForBlankLetter = new ArrayList<>();
        char blankLetter = '_';

        for (ScrabbleLetter scrabbleLetter: word) {
            searchForBlankLetter.add(scrabbleLetter.getLetter() == blankLetter);
        }
        return searchForBlankLetter.contains(true);
    }

    public ScrabbleLetter getBlankLetter() {
        List<Character> checkForBlankLetter = new ArrayList<>();
        for (ScrabbleLetter scrabbleLetter : word) {
            checkForBlankLetter.add(scrabbleLetter.getLetter());
        }
        return word.get(checkForBlankLetter.indexOf('_'));
    }

    @Override
    public String toString() {
        return wordAsString;
    }

    public void replaceLetter(ScrabbleLetter letterToReplace) {
        word.add(word.indexOf(getBlankLetter()), letterToReplace);
        word.remove(getBlankLetter());

    }
}
