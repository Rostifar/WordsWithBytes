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
    List<ScrabbleLetter> blankLetters;
    List<Integer> positionOnRack;

    public ScrabbleWord(String rawWord) {
        word = new ArrayList<>();
        wordAsString = rawWord;
        parseWord(rawWord.toUpperCase());

    }

    public ScrabbleWord(List<ScrabbleLetter> rawListOfLetters) {
        wordAsString = String.valueOf(listToCharArray(rawListOfLetters));
    }


    public List<ScrabbleLetter> lettersInWord() {
        return word;
    }

    public char[] listToCharArray(List<ScrabbleLetter> letterList) {
        char[] arrayOfLetters = new char[letterList.size()];

        for (ScrabbleLetter letter : letterList) {
            arrayOfLetters[letterList.indexOf(letter)] = letter.getLetter();
        }
        return arrayOfLetters;
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
        blankLetters = new ArrayList<>();
        positionOnRack = new ArrayList<>();

        for (ScrabbleLetter scrabbleLetter : word) {

            if (scrabbleLetter.letter == '_') {
                blankLetters.add(scrabbleLetter);
                positionOnRack.add(word.indexOf(scrabbleLetter));
            }
        }
    }


    public List<ScrabbleLetter> getBlankScrabbleLetters() {
        return blankLetters;
    }

    public List<Integer> getBlankScrabbleLetterPostion() {
        return positionOnRack;
    }

    @Override
    public String toString() {
        return wordAsString;
    }

    public void replaceLetter(ScrabbleLetter letterToAdd, int letterLocation) {
        word.add(letterLocation, letterToAdd);
        word.remove(letterLocation + 1);
    }

    public void clearFoundBlankLetters() {
        blankLetters.clear();
    }
}
