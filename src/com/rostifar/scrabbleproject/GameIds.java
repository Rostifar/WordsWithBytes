package com.rostifar.scrabbleproject;

import java.util.Hashtable;
import java.util.List;

/**
 * Created by rostifar on 5/23/16.
 */
 public class GameIds {

    private static Hashtable<Integer, List<String>> gameIds = new Hashtable<>();

    public void addGameToTable(int GAME_ID, List<String> USER_ID) {
        gameIds.put(GAME_ID, USER_ID);
    }

    public void addUserToGame(int GAME_ID, String NEW_USER_ID) {
        gameIds.get(GAME_ID).add(NEW_USER_ID);
    }

    public boolean isGameInTable(int GAME_ID) {
        return gameIds.get(GAME_ID) == null;
    }
}
