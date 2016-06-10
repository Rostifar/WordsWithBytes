package com.rostifar.gamecontrol;

/**
 * Created by rostifar on 6/10/16.
 */
public class ScrabbleGameResources {
    private String gameCode;

    public ScrabbleGameResources() {
        generateGameCode();
    }

    private void generateGameCode() {
        int randomGameCode = (int)(Math.random()*9000)+1000;
        gameCode = String.valueOf(randomGameCode);
    }

    public String getGameCode() {
        return gameCode;
    }
}
