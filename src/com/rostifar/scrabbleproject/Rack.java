package com.rostifar.scrabbleproject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by D14048 on 10/4/2015.
 */
public class Rack {
    protected List<ScrabbleLetter> lettersOnRack;

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
}


