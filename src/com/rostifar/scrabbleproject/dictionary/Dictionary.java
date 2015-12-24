package com.rostifar.scrabbleproject.dictionary;

import java.util.Locale;

/**
 * Defines the public contract for use of a dictionary.
 * Created by GitLazy (Dad) on 12/11/2015.
 */
public interface Dictionary {
    boolean isValidWord(String aWord);
    String getDefinitionForWord(String aWord) throws Exception;
    Locale getLanguage();
}
