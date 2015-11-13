package com.rostifar.scrabbleproject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by D14048 on 10/4/2015.
 */
public class Rack {

    protected List<ScrabbleLetter> lettersOnRack = new ArrayList<>();


    Rack() {
        getLettersCurrentlyOnRack();
    }


    protected void addLetter(ScrabbleLetter scrabbleLetter) {

        lettersOnRack.add(scrabbleLetter);


    }

    protected List getLettersCurrentlyOnRack() {
        return lettersOnRack;
    }


    protected void removeLetter() {

        lettersOnRack.size();







    }
}


