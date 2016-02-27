package com.rostifar.dictionary;
import com.rostifar.gamecontrol.ScrabbleGameException;

/**
 * Created by GitLazy (Dad) on 12/23/2015.
 * Factory class used to construct the various types of Dictionaries and initialize them for use.
 */
public final class DictionaryFactory {

    private static Dictionary dictionary;
    public static Dictionary getDictionary() throws ScrabbleGameException {

        if (dictionary != null)
            return dictionary;

        try {
            dictionary = getDictionaryInstance();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ScrabbleGameException(e.getMessage());
        }
        return dictionary;
    }

    private static Dictionary getDictionaryInstance() throws Exception {
        AbstractDictionary dictionaryInstance = (AbstractDictionary) getDictionaryClass().newInstance();
        return dictionaryInstance.initialize();
    }

   private static  Class<?> getDictionaryClass() throws ClassNotFoundException {
        Class<?> dictionaryClass = Class.forName("com.rostifar.dictionary.WebServiceBasedDictionary");
        return dictionaryClass;
    }

}
