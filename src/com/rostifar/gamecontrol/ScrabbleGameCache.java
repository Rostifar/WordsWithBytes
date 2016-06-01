package com.rostifar.gamecontrol;

import java.util.HashMap;
import java.util.Map;

/**
 * This is a singleton class that will hold a a map of all valid ScrabbleGameManagers keyed by the game code used to start the game.
 * TODO: This is should be cached property using a third party library (EHCache ?) which supports persistence so we don't lose game isntances!
 * Created by Dad and Ross on 5/28/2016.
 */
public class ScrabbleGameCache {
    private static Map gameCache;

    private static Map getGameCache() {
        if (gameCache == null)
            gameCache = new HashMap<String, ScrabbleGameManager>();

        return gameCache;
    }

    /**
     * Add an instance of the GameManager as the value and  the GameID as the key.
     */
    public static void addGame(ScrabbleGameManager gameManager) throws ScrabbleGameException {
        if (gameManager.getGameCode() == null) {
            throw new ScrabbleGameException("Cannot add a GameManager that does not have a game code!");
        }

        getGameCache().put(gameManager.getGameCode(), gameManager);
    }

    /**
     * Remove the value of the GameManager instance associated with the passed in Key.
     * @param gameCode the key that will be used to removed the instance of a GamaManager associated with the key
     * @return the instance of the GameManager that was removed or null if it did not exist
     *
     */
    public static ScrabbleGameManager removeGame(String gameCode) {
        return (ScrabbleGameManager) getGameCache().remove(gameCode);
    }

    /**
     * Return the value of the GameManager instance associated with the passed in Key.
     * @param gameCode the key that will be used to removed the instance of a GamaManager associated with the key
     * @return the instance of the GameManager or null if it did not exist
     *
     */
    public static ScrabbleGameManager lookupGame(String gameCode) {
        return (ScrabbleGameManager) getGameCache().get(gameCode);
    }


}
