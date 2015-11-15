package com.rostifar.scrabbleproject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by D14048 on 10/4/2015.
 */
public class ScrabbleGameManager implements GameManager {

    private boolean isFirstRound;
    private ScrabbleBoard scrabbleBoard;
    private List<Player> players;

    protected ScrabbleGameManager() {
        setupGame();
    }

    @Override
    public void runGame() {
        startGame();
        endGame();
        breakdownGame();
    }

    protected void setupGame() {
        scrabbleBoard = new ScrabbleBoard();
        addPlayers();
    }

    private void addPlayers() {
        players = new ArrayList<>(4);

        for (int playerIdx = 0; playerIdx < 4; playerIdx++) {
            players.add(new Player("Player-" + playerIdx));
        }
    }

    protected void startGame() {
        System.out.println(scrabbleBoard);

    }


    protected void breakdownGame() {

    }


    protected void endGame() {

    }


    protected void determineFirstPlayer() {

    }


    protected void determinePlayerTurn() {

    }
}
