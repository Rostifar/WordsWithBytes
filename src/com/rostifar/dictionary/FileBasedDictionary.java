package com.rostifar.dictionary;

import com.rostifar.gamecontrol.ScrabbleGameException;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Implement a dictionary as a flat file lookup.
 * File is in the format of asingle record with each word delimited by a space
 * Created by GitLazy (Dad) on 12/11/2015.
 */
public class FileBasedDictionary extends AbstractDictionary implements Dictionary {
    private static final String FILE_PATH = "dictionary.txt";
    InputStream inputStream;
    static StringBuilder cache;


    /**
     * Initialize the Dictionary. Required by subclasses of AbstractDictionary
     * @return fully initialized instance of a dictionary, ready for use
     */
    public Dictionary initialize() throws Exception {
        if (cache != null) {
            return this;
        }

        cache = new StringBuilder();
        ClassLoader classLoader = getClass().getClassLoader();
        inputStream = classLoader.getResourceAsStream(FILE_PATH);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;

        while ((line = reader.readLine()) != null) {
            //int readSoFar = inputStream.read(buffer, 0, 4096);

            /*if (readSoFar == -1)
                break;*/

            cache.append(line);
        }

        return this;
    }

    /**
     * Check the word against the dictionary for existence
     * @param aWord does this word exist in the dictionary?
     * @return true if yes, false if no
     */
    public DictionaryLookupResult searchDictionary(String aWord) throws ScrabbleGameException {

        DictionaryLookupResult lookupResult = new DictionaryLookupResult(aWord);

        if (cache.indexOf(' ' + aWord + ' ') > -1) {
            lookupResult.setIsValidWord(true);
            System.out.println("FOUND in Dictionary: " + aWord);
        } else {
            System.out.println("DID NOT FIND in Dictionary: " + aWord);
        }

        return lookupResult;
    }

    @Override
    public DictionaryLookupResult lookupWord(String lookupWord) throws ScrabbleGameException {
        return searchDictionary(lookupWord);
    }
}
