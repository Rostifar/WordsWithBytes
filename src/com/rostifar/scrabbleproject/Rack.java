package com.rostifar.scrabbleproject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by D14048 on 10/4/2015.
 */
public class Rack {

    List<ScrabbleLetter> listLettersOnRack = new ArrayList<>();
    ScrabbleAlphabetImpl scrabbleAlphabet;



    private boolean lettersNeeded;



    private List<ScrabbleLetter> getLetters(int numberOfLettersNeeded) {
        for (int minimumNumberOfLettersNeeded = 0; minimumNumberOfLettersNeeded <= numberOfLettersNeeded; minimumNumberOfLettersNeeded++) {
            listLettersOnRack.add(scrabbleAlphabet.transferScrabbleLetterToRack());
        }
        return listLettersOnRack;
    }

    protected void removeLetter() {

    }
}


