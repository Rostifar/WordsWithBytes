package com.rostifar.dictionary;

import com.rostifar.gamecontrol.ScrabbleGameException;

import java.io.InputStream;
import java.util.Scanner;

/**
 * Implement a dictionary as a flat file lookup.
 * File is in the format of a
 * 1) Header on the first line,
 * 2) All other lines | (pipe) delimited with a unique ID as the first field and the word as the second field.
 * ID|WORD <-- header on first line
 * 120|abalones <-- examples of records in rest of files
 * 166|abashments
 * etc....
 * This same file and format is used to load the database for a databased dictionary implementation.
 * Created by GitLazy (Dad) on 12/11/2015.
 */
public class FileBasedDictionary extends AbstractDictionary implements Dictionary {
    private static final String FILE_PATH = "dictionary.dsv";
    private static final char FIELD_DELIMITER = '|';
    InputStream inputStream;

    /**
     * Make default constructor private to force use of DictionaryFactory
     */
   /* FileBasedDictionary(){
        initialize();
    }*/

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        inputStream.close();
    }

    /**
     * Initialize the Dictionary. Required by subclasses of AbstractDictionary
     * @return fully initialized instance of a dictionary, ready for use
     */
    public Dictionary initialize() throws Exception {
        ClassLoader classLoader = getClass().getClassLoader();
        inputStream = classLoader.getResourceAsStream(FILE_PATH);
        return this;
    }

    /**
     * Check the word against the dictionary for existence
     * @param aWord does this word exist in the dictionary?
     * @return true if yes, false if no
     */

    public DictionaryLookupResult searchDictionary(String aWord) throws ScrabbleGameException {

        DictionaryLookupResult lookupResult = new DictionaryLookupResult(aWord);
        Scanner scanner = new Scanner(inputStream);

        for (int recNumber = 0; scanner.hasNextLine(); recNumber++) {
            String line = scanner.nextLine();

            if (recNumber == 0) //Skip over first line in file which is the header
                continue;

            if (recNumber % 5000 == 0)
                System.out.println("Searched: " + recNumber + " words...");

            String wordInDictionary = line.substring(line.indexOf(FIELD_DELIMITER) + 1);

            if (aWord.equals(wordInDictionary)) {
                lookupResult.setIsValidWord(true);
                System.out.println("FOUND: " + aWord + " at " + recNumber);
                break;
            }
        }

        return lookupResult;
    }

    @Override
    public DictionaryLookupResult lookupWord(String lookupWord) throws ScrabbleGameException {
        return searchDictionary(lookupWord);
    }
}
