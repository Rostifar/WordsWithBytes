package com.rostifar.scrabbleproject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Cal Briden (Dad)  on 8/13/2015.
 */
public class ScrabbleAlphabetFactory implements ScrabbleAlphabet {

    public static ScrabbleAlphabet createInstance() {
        return new ScrabbleAlphabetImpl();
    }

}
