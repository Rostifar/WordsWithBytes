package com.rostifar.scrabbleproject;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.Scanner;

/**
 * Implement a dictionary as a flat file lookup.
 * Created by GitLazy (Dad) on 12/11/2015.
 */
public class FileBasedDictionary implements Dictionary {
    File fileDictionaryOfWords;

    void FileBasedDictionary() throws IOException {
        initialize();
    }

    private void initialize() throws IOException {

        ClassLoader classLoader = getClass().getClassLoader();
        fileDictionaryOfWords = new File(classLoader.getResource("Resources/dictionary.dsv").getFile());

        if (!fileDictionaryOfWords.exists()) {
           throw new IOException("File: Resources/dictionary.dsv could not be opened");
        }
    }

    /**
     * Chech the word against the dictionary for existance
     * @param aWord does htis word exist in the dictionary?
     * @return true if yes, false if no
     */
    @Override
    public boolean isValidWord(String aWord) {

        boolean foundWord = false;

        try (Scanner scanner = new Scanner(fileDictionaryOfWords)) {

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                if (aWord.equals(line.trim())) {
                    foundWord = true;
                    break;
                }
            }

            scanner.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return foundWord;
    }

    @Override
    public String getDefinitionForWord(String aWord) throws ScrabbleGameInvalidWordException {
        throw new ScrabbleGameInvalidWordException(aWord + " Dictionary definitions not available");
    }

    /**
     * As of now we are only supporting english...
     */
    @Override
    public Locale getLanguage() {
        return Locale.ENGLISH;
    }
}
