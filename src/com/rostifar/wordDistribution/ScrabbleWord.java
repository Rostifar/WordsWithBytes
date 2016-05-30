package com.rostifar.wordDistribution;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ross on 12/19/15.
 */
public class ScrabbleWord implements Serializable {
    private List<ScrabbleLetter> word;
    private String wordAsString;

    /**
     * @ScrabbleWord[constructor]
     * purpose-> constructor which takes in a String, char[], or list and converts it to a ScrabbleWord
     * */
    public ScrabbleWord(String rawWord) {
        word = new ArrayList<>();
        wordAsString = rawWord;
        parseWord(rawWord.toUpperCase());
    }

    public ScrabbleWord(char[] rawWord) {
        word = new ArrayList<>();
        wordAsString = String.valueOf(rawWord);
        parseWord(wordAsString.toUpperCase());
    }

    public ScrabbleWord(List<ScrabbleLetter> rawListOfLetters) {
        wordAsString = String.valueOf(listToCharArray(rawListOfLetters));
    }

    /**
     * @lettersInWord
     * purpose-> returns the ScrabbleLetters in a word in a List format
     * */
    public List<ScrabbleLetter> lettersInWord() {
        return word;
    }

    /**
     *@listToCharArray
     * purpose-> converts a list of ScrabbleLetters to a char[]
     *  */
    public char[] listToCharArray(List<ScrabbleLetter> letterList) {
        char[] arrayOfLetters = new char[letterList.size()];

        for (ScrabbleLetter letter : letterList) {
            arrayOfLetters[letterList.indexOf(letter)] = letter.getLetter();
        }
        return arrayOfLetters;
    }

    /**
     * @getLetterAt
     * purpose-> using an index, gets the designated ScrabbleLetter in a word
     * */
    public ScrabbleLetter getLetterAt(int index) {
        return word.get(index);
    }

    /**
     * @getNumberOfLetters
     * purpose-> returns the size of a word
     * */
    public int getNumberOfLetters() {
        return word.size();
    }

    /**
     *@parseWord
     * purpose-> for each provided character in a word, converts that character to a ScrabbleLetter object
     */
    private void parseWord(String rawWord) {
        char[] lettersUsed = rawWord.toCharArray();

        for (char letter : lettersUsed) {
            word.add(new ScrabbleLetter(letter));
        }
    }

    @Override
    public String toString() {
        return wordAsString;
    }
}
