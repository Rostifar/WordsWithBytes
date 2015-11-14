package com.rostifar.scrabbleproject;

import java.util.List;

/**
 * Created by Cal (Dad) on 8/19/2015.
 * This is the public interface for the Scrabble Alphabet. It (should) contain actions that will provide service
 * to the Scrabble game and it's players.
 *
 */
public interface ScrabbleAlphabet {

   List<Character> getAvailableLetters();
}
