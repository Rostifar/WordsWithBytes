package com.rostifar.scrabbleproject;
import java.util.*;

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
        letterMap.put (' ', createDuplicateLetters(' ', 2));

        letterMap.put('L', createDuplicateLetters('L', 4));
        letterMap.put('S', createDuplicateLetters('S', 4));
        letterMap.put('U', createDuplicateLetters('U', 4));

        letterMap.put('N', createDuplicateLetters('N', 6));
        letterMap.put('R', createDuplicateLetters('R', 6));
        letterMap.put('T', createDuplicateLetters('T', 6));

        letterMap.put('O', createDuplicateLetters('O', 8));

        letterMap.put('A', createDuplicateLetters('A', 9));
        letterMap.put('I', createDuplicateLetters('I', 9));

        letterMap.put('E', createDuplicateLetters('E', 12));

        letterMap.put('D', createDuplicateLetters('D', 4));
        letterMap.put('G', createDuplicateLetters('G', 3));

        letterMap.put('B', createDuplicateLetters('B', 2));
        letterMap.put('C', createDuplicateLetters('C', 2));
        letterMap.put('M', createDuplicateLetters('M', 2));
        letterMap.put('P', createDuplicateLetters('P', 2));

        letterMap.put('F', createDuplicateLetters('F', 2));
        letterMap.put('H', createDuplicateLetters('H', 2));
        letterMap.put('V', createDuplicateLetters('V', 2));
        letterMap.put('W', createDuplicateLetters('W', 2));
        letterMap.put('Y', createDuplicateLetters('Y', 2));

        letterMap.put('K', createDuplicateLetters('K', 1));

        letterMap.put('J', createDuplicateLetters('J', 1));
        letterMap.put('X', createDuplicateLetters('X', 1));

        letterMap.put('Q', createDuplicateLetters('Q', 1));
        letterMap.put('Z', createDuplicateLetters('Z', 1));
    }

    /**
     * Create multiple instances of the same letter.
     *
     * @param letter scrabble alphabet letter to duplicate
     * @param numberOfDuplications number of duplicates to create
     * @return result List of duplicate letters
     */
    private List<ScrabbleLetter> createDuplicateLetters(char letter, int numberOfDuplications) {
        List<ScrabbleLetter> duplicateListOfLetters = new ArrayList<>();

        for (int dupCntr = 0; dupCntr < numberOfDuplications; dupCntr++) {
            duplicateListOfLetters.add(new ScrabbleLetter(letter));


        }

        return duplicateListOfLetters;
    }

    /**
     * @return a list containing the letters that are available for use
     */
    public List<Character> getAvailableLetters() {
        List<Character> listOfAvaiableLetters = new ArrayList<>();

        for (char letterToCheck : letterMap.keySet()) {
            if (!letterMap.get(letterToCheck).isEmpty()) {
                listOfAvaiableLetters.add(letterToCheck);
            }
        }

        return listOfAvaiableLetters;
    }

    //TODO: Figure out what to do with this?
    public List<ScrabbleLetter> getLetters(int numberOfLetters) {
        List<ScrabbleLetter> letters;

        for (int letterCnt = 0; letterCnt < numberOfLetters; letterCnt++) {
        }

        return getLetters(9);

    }


    protected void transferScrabbleLetterToRack(Rack rack) {
        Random random = new Random();
        char randomlySelectedKey = getAvailableLetters().get(random.nextInt(getAvailableLetters().size()));

        List<ScrabbleLetter> letterList = letterMap.get(randomlySelectedKey);
        ScrabbleLetter letterToRemove = letterList.get(random.nextInt(letterList.size()));

    }



}