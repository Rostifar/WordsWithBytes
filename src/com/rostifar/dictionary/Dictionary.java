package com.rostifar.dictionary;

import com.rostifar.gamecontrol.ScrabbleGameException;

/**
 * Defines the public contract for use of a dictionary.
 * Created by GitLazy (Dad) on 12/11/2015.
 */
public interface Dictionary {
    DictionaryLookupResult lookupWord(String lookupWord) throws ScrabbleGameException;
    //boolean isValidWord(DictionaryLookupResult dictionaryLookupResult);
}
