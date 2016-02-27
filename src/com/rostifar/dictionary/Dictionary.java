package com.rostifar.dictionary;

/**
 * Defines the public contract for use of a dictionary.
 * Created by GitLazy (Dad) on 12/11/2015.
 */
public interface Dictionary {
    DictionaryLookupResult lookupWord(String lookupWord) throws Exception;
    //boolean isValidWord(DictionaryLookupResult dictionaryLookupResult);
}
