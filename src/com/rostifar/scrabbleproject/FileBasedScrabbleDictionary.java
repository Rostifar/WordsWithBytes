package com.rostifar.scrabbleproject;

import java.io.File;
import java.util.Locale;

/**
 * Implement a dictionary as a flat file lookup.
 * Created by GitLazy (Dad) on 12/11/2015.
 */
public class FileBasedScrabbleDictionary implements Dictionary {
    File fileDictionaryOfWords;

    void FileBasedScrabbleDictionary() {

    }

    @Override
    public boolean isValidWord(String aWord) {
        return true;
    }

    @Override
    public String getDefinitionForWord(String aWord) throws ScrabbleGameInvalidWordException {
        throw new ScrabbleGameInvalidWordException(aWord + "does not exist in the Dictionary");
    }

    @Override
    public Locale getLanguage() {
        return Locale.ENGLISH;
    }
}
