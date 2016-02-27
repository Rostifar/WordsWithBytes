package com.rostifar.gamecontrol;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by D14048 on 2/25/2016.
 */
public class ScrabbleGameConfiguration {
    /**
     * Load the properties file from the Resources folder.
     *
     * @throws Exception if file cannot be found or loaded
     */
    private static Properties gameProperties = new Properties();
    private static final String PROPERTY_FILE = "scrabbleGame.properties";

    public static void initialize() throws ScrabbleGameException{
        loadProperties();
    }

    public static Properties getProperties() {
        return gameProperties;
    }

    private static void loadProperties() throws ScrabbleGameException {
        gameProperties = new Properties();

        try {
            gameProperties.load(ScrabbleGameConfiguration.class.getClassLoader().getResourceAsStream(PROPERTY_FILE));
        } catch (IOException ioExp) {
            throw new ScrabbleGameException(PROPERTY_FILE + " - could not be found or loaded");
        }
    }
}

