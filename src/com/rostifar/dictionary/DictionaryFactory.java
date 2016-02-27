package com.rostifar.dictionary;
import com.rostifar.gamecontrol.ScrabbleGameConfiguration;
import com.rostifar.gamecontrol.ScrabbleGameException;

/**
 * Created by GitLazy (Dad) on 12/23/2015.
 * Factory class used to construct the various types of Dictionaries and initialize them for use.
 */
public final class DictionaryFactory {

    private static Dictionary dictionary;
    private static Class<?> dictionaryClass;
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

    /**
     * Example: "com.rostifar.dictionary.WebServiceBasedDictionary"
     * @return
     * @throws ClassNotFoundException
     */
   private static  Class<?> getDictionaryClass() throws ClassNotFoundException {
        if (dictionaryClass == null) {
            dictionaryClass = Class.forName(ScrabbleGameConfiguration.getProperties().getProperty("dictionaryClass"));
        }

        return dictionaryClass;
    }

    /**
     * Implemented for Unit testing purposes only!
     * @param aClassName a String representing a class (with prepended package) name that implements
     *  the Dictionary interface.
     * @throws ClassNotFoundException if the class cannot be found or loaded
     */
    public static void setDictionaryClass(String aClassName) throws ClassNotFoundException {
        if (aClassName == null) {
            dictionaryClass = null;
        }
        else {
            dictionaryClass = Class.forName(aClassName);
            dictionary = null;
        }
    }

}
