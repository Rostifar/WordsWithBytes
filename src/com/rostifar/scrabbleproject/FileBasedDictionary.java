package com.rostifar.scrabbleproject;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.Scanner;

/**
 * Implement a dictionary as a flat file lookup.
 * File is in the format of a
 * 1) Header on the first line,
 * 2) All other lines | (pipe) delimited with a unique ID as the first field and the word as the second field.
 * ID|WORD <-- header on first line
 * 120|abalones <-- examples of records in rest of files
 * 166|abashments
 * etc....
 * This same file and format is used to load the database for a databased dictionary implementation.
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
     * Chech the word against the dictionary for existence
     * @param aWord does this word exist in the dictionary?
     * @return true if yes, false if no
     */
    @Override
    public boolean isValidWord(String aWord) {

        boolean foundWord = false;

        try (Scanner scanner = new Scanner(fileDictionaryOfWords)) {

            for (int recNumber = 0; scanner.hasNextLine(); recNumber++) {
                String line = scanner.nextLine();

                if (recNumber == 0) //Skip over first line in file which is the header
                    continue;

                String wordInDictionary = line.trim().split("|")[1];

                if (aWord.equals(wordInDictionary)) {
                    foundWord = true;
                    break;
                }
            }

            scanner.close();

        } catch (IOException e) {
            e.printStackTrace();
            //FIXME: throw new Exception?
        }

        return foundWord;
    }

    @Override
    public String getDefinitionForWord(String aWord) throws ScrabbleGameInvalidWordException {
        throw new ScrabbleGameInvalidWordException(aWord + " - Dictionary definitions not available");
    }

    /**
     * As of now we are only supporting english...
     */
    @Override
    public Locale getLanguage() {
        return Locale.ENGLISH;
    }
}
