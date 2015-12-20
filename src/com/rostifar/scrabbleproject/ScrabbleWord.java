package com.rostifar.scrabbleproject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ross on 12/19/15.
 */
public class ScrabbleWord {
    private List<ScrabbleLetter> word;

    public ScrabbleWord(String rawWord) {
        parseWord(rawWord);
    }

    /**
    *Takes apart userinputed word and converts each Character to a ScrabbleLetter, In order to validate the user actually has these letters in their Rack.
    * Places each instance of a ScrabbleLetter into an array list
    */
    private void parseWord(String rawWord) {

        word = new ArrayList<>();
        char[] lettersUsed = rawWord.toCharArray();
        for (char letter : lettersUsed) {
            word.add(new ScrabbleLetter(letter));
        }
    }
}
