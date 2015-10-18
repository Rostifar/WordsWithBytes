package com.rostifar.scrabbleproject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Cal Briden (Dad)
 * Concrete implementation of ScrabbleAlphabet
 */
public class ScrabbleAlphabetImpl implements ScrabbleAlphabet {
    private Map<Character, List<ScrabbleLetter>> letterMap = new HashMap<>();

    /**
     * Constructor is private. Use Factory class to create instances.
     */
    protected ScrabbleAlphabetImpl() {
        loadLetters();
    }


    /**
     * Load Scrabble letters for the various point values
     */
    private void loadLetters() {

        ArrayList<ScrabbleLetter> lettersValuedAtOnePoint = new ArrayList<>();

        letterMap.put(Character.valueOf(' '), createDuplicateLetters(' ' , 2));

        letterMap.put(Character.valueOf('L'), createDuplicateLetters('L', 4));
        letterMap.put(Character.valueOf('S'), createDuplicateLetters('S', 4));
        letterMap.put(Character.valueOf('U'), createDuplicateLetters('U', 4));

        letterMap.put(Character.valueOf('N'), createDuplicateLetters('N', 6));
        letterMap.put(Character.valueOf('R'), createDuplicateLetters('R', 6));
        letterMap.put(Character.valueOf('T'), createDuplicateLetters('T', 6));

        letterMap.put(Character.valueOf('O'), createDuplicateLetters('O', 8));

        letterMap.put(Character.valueOf('A'), createDuplicateLetters('A', 9));
        letterMap.put(Character.valueOf('I'), createDuplicateLetters('I', 9));

        letterMap.put(Character.valueOf('E'), createDuplicateLetters('E', 12));
    }

    /**
     * Create multiple instances of the same letter.
     * @param letter scrabble alphabet letter to duplicate
     * @param numberOfDuplications number of duplicates to create
     * @return result List of duplciate letters
     */
    private List<ScrabbleLetter> createDuplicateLetters(char letter, int numberOfDuplications) {
        List<ScrabbleLetter> duplicateListOfLetters;
        duplicateListOfLetters = new ArrayList<>();

        for (int dupCntr = 0; dupCntr < numberOfDuplications; dupCntr++) {
            duplicateListOfLetters.add(new ScrabbleLetter(letter));
        }
        return duplicateListOfLetters;
    }

}
