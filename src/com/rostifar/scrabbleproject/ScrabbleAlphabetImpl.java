package com.rostifar.scrabbleproject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScrabbleAlphabetImpl implements ScrabbleAlphabet {
    private Map<Character, List<ScrabbleLetter>> letterMap = new HashMap<>();


    public void sortLettersByNumberValue() {

        ArrayList<ScrabbleLetter> lettersValuedAtZeroPoints = new ArrayList<>();

        ArrayList<ScrabbleLetter> lettersValuedAtOnePoint = new ArrayList<>();

        ArrayList<ScrabbleLetter> lettersValuedAtTwoPoints = new ArrayList<>();

        ArrayList<ScrabbleLetter> letterValuedAtThreePoints = new ArrayList<>();

        ArrayList<ScrabbleLetter> lettersValuedAtFourPoints = new ArrayList<>();

        ArrayList<ScrabbleLetter> lettersValuedAtFivePoints = new ArrayList<>();

        ArrayList<ScrabbleLetter> lettersValuedAtEightPoints = new ArrayList<>();

        ArrayList<ScrabbleLetter> lettersValuedAtTenPoints = new ArrayList<>();

    }

    /**
     *
     *
     * Constructor is private. Use Factory class to create instances.
     */
    protected ScrabbleAlphabetImpl() {
        loadLetters();
    }


    /**
     *
     * Load Scrabble letters for the various point values
     */
    private void loadLetters() {



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

        letterMap.put(Character.valueOf('D'), createDuplicateLetters('D', 4));
        letterMap.put(Character.valueOf('G'), createDuplicateLetters('G', 3));

        letterMap.put(Character.valueOf('B'), createDuplicateLetters('B', 2));
        letterMap.put(Character.valueOf('C'), createDuplicateLetters('C', 2));
        letterMap.put(Character.valueOf('M'), createDuplicateLetters('M', 2));
        letterMap.put(Character.valueOf('P'), createDuplicateLetters('P', 2));

        letterMap.put(Character.valueOf('F'), createDuplicateLetters('F', 2));
        letterMap.put(Character.valueOf('H'), createDuplicateLetters('H', 2));
        letterMap.put(Character.valueOf('V'), createDuplicateLetters('V', 2));
        letterMap.put(Character.valueOf('W'), createDuplicateLetters('W', 2));
        letterMap.put(Character.valueOf('Y'), createDuplicateLetters('Y', 2));

        letterMap.put(Character.valueOf('K'), createDuplicateLetters('K', 1));

        letterMap.put(Character.valueOf('J'), createDuplicateLetters('J', 1));
        letterMap.put(Character.valueOf('X'), createDuplicateLetters('X', 1));

        letterMap.put(Character.valueOf('Q'), createDuplicateLetters('Q', 1));
        letterMap.put(Character.valueOf('Z'), createDuplicateLetters('Z', 1));








    }

    /**
     * Create multiple instances of the same letter.
     * @param letter scrabble alphabet letter to duplicate
     * @param numberOfDuplications number of duplicates to create
     * @return result List of duplciate letters
     */



    private List<ScrabbleLetter> createDuplicateLetters(char letter, int numberOfDuplications) {
        List<ScrabbleLetter> duplicateListOfLetters = new ArrayList<>();

        for (int dupCounter = 0; dupCounter < numberOfDuplications; dupCounter++) {
            duplicateListOfLetters.add(new ScrabbleLetter());
        }
        return duplicateListOfLetters;

    }

}
