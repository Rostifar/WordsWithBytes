package com.rostifar.scrabbleproject;

import com.rostifar.scrabbleproject.dictionary.AbstractDictionary;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;
import java.util.Scanner;

/**
 * Created by D14048 on 10/4/2015.
 */
public class ScrabbleGameManager implements GameManager {

    private ScrabbleBoard scrabbleBoard;
    private Player players[];
    private UserInput userInput;
    private ScrabbleAlphabetImpl scrabbleAlphabet = new ScrabbleAlphabetImpl();
    private String greeting = "Hello, Welcome to WordsWithBytes!";
    private Player currentPlayer;
    private ScrabbleWord scrabbleWord;
    private Rack rack;
    private AbstractDictionary dictionary;

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
        System.out.println(greeting);
        System.out.println("Setting up Scrabble game...");
        scrabbleBoard = new ScrabbleBoard();
        System.out.println(scrabbleBoard);
        addPlayers();
        playerManager();
    }

    /**
     * Prompt the user for how many players to add
     */
    private void addPlayers() {

        int numberOfPlayers;

        try {
            String input = userInput.getInputFromUser("How many players? (enter 1,2,3 or 4:");
            numberOfPlayers = Integer.valueOf(input);
        } catch (Exception ue) {
            System.out.println("Error getting user input, defaulting to 2 players");
            numberOfPlayers = 2;
        }
        players = new Player[numberOfPlayers];

        for (int playerIdx = 0; playerIdx < numberOfPlayers; playerIdx++) {
            Player player = new Player("Player-" + playerIdx);
            setupPlayer(player);
            players[playerIdx] = player;
        }

        printPlayers();
    }

    protected void setupPlayer(Player player) {
        if (player.needsLetters()) {
            player.addLetters(scrabbleAlphabet.getLetters(player.getNumberOfLettersNeeded()));
        }
    }


    private void playWord() {

        scrabbleWord = new ScrabbleWord(userInput.getInputFromUser("Enter your desired word: "));

        if (currentPlayer.isValidWord(scrabbleWord)) {
            currentPlayer.removeLetters(scrabbleWord);
            getLetters();
        } else {
            System.out.println("Error! You do not have the letters you have selected on your Rack. Please play another word.");
            makeMove();
        }

        try {
            dictionary.isValidWord(scrabbleWord.toString());
        } catch (Exception e) {
            if (!isValidInput(scrabbleWord.toString())) {
                System.out.println("Error! the word you selected is not a real word. Please try again.");
                makeMove();
            }
        }

    }






    private void printPlayers() {


        for (Player playr : players) {
            System.out.println(playr);
        }
    }

    private boolean isValidInput(String input) {
        final int MAX_INPUT_LENGTH = 1;

        return (input.length() == MAX_INPUT_LENGTH);
    }

    private void getLetters() {
        if (currentPlayer.needsLetters()) {
            currentPlayer.addLetters(scrabbleAlphabet.getLetters(currentPlayer.getNumberOfLettersNeeded()));
        }
    }

    private void exchangeLetters() {
        currentPlayer.getLettersToExchange(userInput.getInputFromUser("Which letters would you like to exchange? ").toUpperCase().toCharArray());
        getLetters();
    }

    private void makeMove() {
        String moveSelected;
        boolean takingTurn = true;

        while (takingTurn) {

            String input = userInput.getInputFromUser("Make a move(s = skip turn, p = play word, e = exchange letters): ");
            moveSelected = String.valueOf(input);

            if (!isValidInput(moveSelected)) {
                System.out.println("invalid entry, try again");
                continue;
            }

            switch (moveSelected) {

                case ("p"):
                    playWord();

                    takingTurn = false;
                    break;
                case ("s"):

                    takingTurn = false;
                    break;
                case ("e"):
                    exchangeLetters();
                    takingTurn = false;
                    break;

                default:
                    break;
            }


        }
    }


    private void playerManager() {

        for (int currntIdx = 0; currntIdx < players.length; currntIdx++) {

            currentPlayer = players[currntIdx];

            System.out.println("\n");
            System.out.println(currentPlayer);
            makeMove();
            if (currntIdx == players.length - 1) {
                currntIdx = -1;
            }
        }
    }


    protected void startGame() {
    }


    protected void breakdownGame() {

    }


    protected void endGame() {

    }
}