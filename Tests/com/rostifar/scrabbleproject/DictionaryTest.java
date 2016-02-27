package com.rostifar.scrabbleproject;

import com.rostifar.dictionary.Dictionary;
import com.rostifar.dictionary.DictionaryFactory;
import com.rostifar.dictionary.DictionaryLookupResult;
import com.rostifar.gamecontrol.ScrabbleGameConfiguration;
import com.rostifar.gamecontrol.ScrabbleGameException;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by GitLazy (Dad)  on 12/22/2015.
 */
public class DictionaryTest {

    @Before
    public void setUp() {
        try {
            ScrabbleGameConfiguration.initialize();
        } catch (ScrabbleGameException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testIsValidWordOnDefaultDictionary() throws Exception {
        DictionaryFactory.setDictionaryClass(null);
        Dictionary dictionary = DictionaryFactory.getDictionary();
        DictionaryLookupResult lookupResult = dictionary.lookupWord("dog");
        assert lookupResult.isValidWord();
    }

    @Test
    public void testIsValidWordOnWebServiceDictionary() throws Exception {
        DictionaryFactory.setDictionaryClass("com.rostifar.dictionary.WebServiceBasedDictionary");
        Dictionary dictionary = DictionaryFactory.getDictionary();
        DictionaryLookupResult lookupResult = dictionary.lookupWord("dog");
        assert lookupResult.isValidWord();
    }

    @Test
    public void testNotValidWordOnWebServiceDictionary() throws Exception {
        DictionaryFactory.setDictionaryClass("com.rostifar.dictionary.WebServiceBasedDictionary");
        Dictionary dictionary = DictionaryFactory.getDictionary();
        DictionaryLookupResult lookupResult = dictionary.lookupWord("abcdefg");
        assert !lookupResult.isValidWord();
    }

    @Test
    public void testIsValidWordOnFileDictionary() throws Exception {
        DictionaryFactory.setDictionaryClass("com.rostifar.dictionary.FileBasedDictionary");
        Dictionary dictionary = DictionaryFactory.getDictionary();
        DictionaryLookupResult lookupResult = dictionary.lookupWord("dog");
        assert lookupResult.isValidWord();
    }

    @Test
    public void testNotValidWordOnFileDictionary() throws Exception {
        DictionaryFactory.setDictionaryClass("com.rostifar.dictionary.FileBasedDictionary");
        Dictionary dictionary = DictionaryFactory.getDictionary();
        DictionaryLookupResult lookupResult = dictionary.lookupWord("abcdefg");
        assert !lookupResult.isValidWord();
    }

}