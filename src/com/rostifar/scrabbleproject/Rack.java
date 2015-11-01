package com.rostifar.scrabbleproject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by D14048 on 10/4/2015.
 */
public class Rack {

    List<ScrabbleLetter> lettersOnRack = new ArrayList<>();

    private boolean lettersNeeded;

    protected List<ScrabbleLetter> getLetters(ScrabbleLetter newScrabbleLetter) {

        lettersOnRack.add(newScrabbleLetter);

        return lettersOnRack;
    }

    protected void removeLetter() {

    }
}


