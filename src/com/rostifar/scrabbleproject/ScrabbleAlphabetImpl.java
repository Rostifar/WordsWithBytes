package com.rostifar.scrabbleproject;
import java.util.*;

public class ScrabbleAlphabetImpl implements ScrabbleAlphabet {
    private Map<Character, List<ScrabbleLetter>> letterMap = new HashMap<>();

    static String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ_";



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



        letterMap.put(Character.valueOf(' '), createDuplicateLetters(' ', 2));

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

        //Basic notation for removing letter from map through list: map.get(Character.valueOf('Insert Letter')).remove(number of object);




    }

    /**
     * Create multiple instances of the same letter.
     *
     * @param letter scrabble alphabet letter to duplicate
     * @param numberOfDuplications number of duplicates to create
     * @return result List of duplciate letters
     */


    private List<ScrabbleLetter> createDuplicateLetters(char letter, int numberOfDuplications) {
        List<ScrabbleLetter> duplicateListOfLetters = new ArrayList<ScrabbleLetter>();

        for (int dupCntr = 0; dupCntr < numberOfDuplications; dupCntr++) {
            duplicateListOfLetters.add(new ScrabbleLetter(letter));


        }

        return duplicateListOfLetters;

    }

    /**
     *Out of a list of keys a random key is drawn.
     *Then an instance of a ScrabbleLetter is removed from the letterList.
     * Which is then placed on the rack, waiting for the player to use that letter.*/


    protected void transferScrabbleLetterToRack() {

        int numberOfLettersRequiredToBeOnRack = 7;


        Random random = new Random();

        char[] arrayOfLetterKeys = alphabet.toCharArray();


        char randomlySelectedKey = arrayOfLetterKeys[random.nextInt(alphabet.length())];

        List<ScrabbleLetter> letterList = letterMap.get(Character.valueOf(randomlySelectedKey));

        ScrabbleLetter letterToRemove = letterList.listIterator().next();//Checks whether there is a letter to remove within the selected key.

        if (letterToRemove != null) { //if there is a letter to remove, remove that letter and move it to the rack.
            ScrabbleLetter removedLetter = letterList.remove(random.nextInt(letterList.size()));



        }
    }



}
