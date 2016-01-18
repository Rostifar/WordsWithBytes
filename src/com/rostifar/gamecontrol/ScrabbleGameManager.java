package com.rostifar.gamecontrol;


import com.rostifar.scabbleboard.ScrabbleBoard;
import com.rostifar.scabbleboard.ScrabbleBoardMechanics;
import com.rostifar.wordDistrobution.BlankScrabbleLetter;
import com.rostifar.wordDistrobution.ScrabbleAlphabetImpl;
import com.rostifar.wordDistrobution.ScrabbleLetter;
import com.rostifar.wordDistrobution.ScrabbleWord;
import com.rostifar.scrabbleproject.*;
import com.rostifar.dictionary.Dictionary;
import com.rostifar.dictionary.DictionaryFactory;


/**
 * Created by D14048 on 10/4/2015.
 */
public class ScrabbleGameManager implements GameManager {

    private ScrabbleBoard scrabbleBoard;
    private Player players[];
    private UserInput userInput;
    private ScrabbleAlphabetImpl scrabbleAlphabet = new ScrabbleAlphabetImpl();
    private Player currentPlayer;
    private Rack playerRack;
    private ScrabbleWord scrabbleWord;




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

    private void isWordOnRack(ScrabbleWord scrabbleWord) {
        if (currentPlayer.isValidWord(scrabbleWord)) {
            currentPlayer.removeLetters(scrabbleWord);
            getLetters();
        } else {
            System.out.println("Error! You do not have the letters you have selected on your Rack. Please play another word.");
            makeMove();
        }
    }


    private void playWord() {

        scrabbleWord = new ScrabbleWord(userInput.getInputFromUser("Enter your desired word: "));

        if (scrabbleWord.containsBlankLetter()) {
            scrabbleWord.replaceLetter(exchangeBlankLetter(scrabbleWord.getBlankLetter()));
        }
        isWordOnRack(scrabbleWord);
        System.out.println(scrabbleBoard);
        int col = Integer.parseInt(userInput.getInputFromUser("At what column would you like to place your selected word ? "));
        int row = Integer.parseInt(userInput.getInputFromUser("At what row would you like to place your selected word ? "));
        String orientation = userInput.getInputFromUser("Would you like your selected word to go horizontal or vertical ? (ie. v or h)");

        if (scrabbleBoard.squareContainsLetter(col, row)) {
            System.out.println("Error the location you have selected has been already used. ");
            makeMove();
        } else {
            scrabbleBoard.setWordCol(col);
            scrabbleBoard.setWordRow(row);
            scrabbleBoard.setUserSelectedOrientation(orientation);
            scrabbleBoard.getScrabbleBoardInstance(scrabbleBoard);
            scrabbleBoard.addWordToBoard(currentPlayer.getRack().getLettersToRemove());
            currentPlayer.getScoreKeeper().getWordPointValue(scrabbleBoard.getWordPointValue());
            scrabbleBoard.clearWordPointValue();
            removeWordFromSelection();
            System.out.println(scrabbleBoard);
            System.out.println(currentPlayer.getCurrentPlayerScore());
        }
    }

    public ScrabbleLetter exchangeBlankLetter(ScrabbleLetter blankLetter) {
        System.out.println(scrabbleAlphabet.getListOfLetters());
        char selectedLetter = userInput.getInputFromUser("The word you have played contains a blank letter. Please select the letter you would like to exchange it for: ").toUpperCase().charAt(0);
        ScrabbleLetter newScrabbleLetter = new BlankScrabbleLetter(selectedLetter);
        currentPlayer.getRack().replaceBlankLetter(newScrabbleLetter, blankLetter);

        return newScrabbleLetter;
    }

    private void removeWordFromSelection() {
        playerRack.getLettersToRemove().removeAll(playerRack.getLettersToRemove());
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

    private boolean isWordInDictionary(String word) {
        Dictionary dictionary = null;
        try {
            dictionary = DictionaryFactory.getDictionary();
        } catch (ScrabbleGameException e) {
            e.printStackTrace();
        }
        return dictionary.isValidWord(word);
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
                case ("quit"):

                    makeMove();
                    break;
                default:
                    break;
            }


        }
    }


    private void playerManager() {

        for (int currntIdx = 0; currntIdx < players.length; currntIdx++) {

            currentPlayer = players[currntIdx];
            playerRack = currentPlayer.getRack();

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