package com.rostifar.scrabbleproject;

import com.rostifar.scrabbleproject.dictionary.Dictionary;
import com.rostifar.scrabbleproject.dictionary.DictionaryFactory;
import org.junit.Before;
import org.junit.Test;

import java.util.Locale;

/**
 * Created by GitLazy (Dad)  on 12/22/2015.
 */
public class FileBasedDictionaryTest {

    Dictionary dictionary;


    @Before
    public void setUp() {

        try {
            dictionary = DictionaryFactory.getDictionary();
        } catch (ScrabbleGameException scrabbleExp) {
            scrabbleExp.printStackTrace();
        }
    }


    @Test
    public void testIsValidWord() throws Exception {
        dictionary.isValidWord("Cat");
    }

    @Test (expected = Exception.class)
    public void testGetDefinitionForWord() throws Exception {
       dictionary.getDefinitionForWord("Cat");
    }

    @Test
    public void testGetLanguage() throws Exception {
        assert dictionary.getLanguage().equals(Locale.ENGLISH);
    }
}