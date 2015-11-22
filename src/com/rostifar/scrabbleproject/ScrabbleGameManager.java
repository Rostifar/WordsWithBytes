package com.rostifar.scrabbleproject;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by D14048 on 10/4/2015.
 */
public class ScrabbleGameManager implements GameManager {

    private boolean isFirstRound;
    private ScrabbleBoard scrabbleBoard;
    private List<Player> players;
    private UserInput userInput;
    private ScrabbleAlphabetImpl scrabbleAlphabet = new ScrabbleAlphabetImpl();

    protected ScrabbleGameManager() {
        userInput = new UserInput();
        setupGame();
    }

    @Override
    public void runGame() {

        startGame();
        endGame();
        breakdownGame();
    }

    protected void setupGame() {
        System.out.println("Setting up Scrabble game...");
        scrabbleBoard = new ScrabbleBoard();
        System.out.println(scrabbleBoard);
        addPlayers();
    }

    /**
     * Prompt the user for how many players to add
     */
    private void addPlayers() {
        players = new ArrayList<>(4);

        int numberOfPlayers = 1;

        try {
            String input = userInput.getInputFromUser("How many players? (enter 1,2,3 or 4:");
            numberOfPlayers = Integer.valueOf(input);
        } catch (Exception ue) {
            System.out.println("Error getting user input, defaulting to 2 players");
            numberOfPlayers = 2;
        }

        for (int playerIdx = 0; playerIdx < numberOfPlayers; playerIdx++) {
            Player player = new Player("Player-" + playerIdx);
            setupPlayer(player);
            players.add(player);
        }
    }

    protected void setupPlayer(Player player) {
        if (player.needsLetters()) {
            player.addLetters(scrabbleAlphabet.getLetters(player.getNumberOfLettersNeeded()));
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
