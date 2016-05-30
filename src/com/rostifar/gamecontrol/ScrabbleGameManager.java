package com.rostifar.gamecontrol;

import com.rostifar.dictionary.Dictionary;
import com.rostifar.dictionary.DictionaryFactory;
import com.rostifar.dictionary.DictionaryLookupResult;
import com.rostifar.scabbleboard.ScrabbleBoard;
import com.rostifar.scrabbleproject.Player;
import com.rostifar.wordDistribution.*;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Created by Dad on 10/4/2015.
 */
public class ScrabbleGameManager implements Serializable {
    private ScrabbleBoard scrabbleBoard;
    private Player players[];
    private ScrabbleAlphabet scrabbleAlphabet = new ScrabbleAlphabet();
    private Player currentPlayer;
    private ScrabbleWord scrabbleWord;
    private boolean isFirstRound = true;
    private ScrabbleWord currentWord;
    private char[] word;


    public ScrabbleGameManager() throws ScrabbleGameException {
        setupGame();
    }

    private void setupGame() throws ScrabbleGameException {
        System.out.println("Setting up Scrabble game...");
        loadConfig();
        scrabbleBoard = new ScrabbleBoard();
    }

    /**
     * Prompt the user for how many players to add
     */
    public void addPlayers(int numberOfPlayers) {

        players = new Player[numberOfPlayers];

        for (int playerIdx = 0; playerIdx < numberOfPlayers; playerIdx++) {
            Player player = new Player("Player-" + playerIdx);
            setupPlayer(player);
            players[playerIdx] = player;
        }

        //Default to first player for now
        currentPlayer = players[0];

        printPlayers();
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public ScrabbleBoard getScrabbleBoard() {
        return scrabbleBoard;
    }

    public Player[] getPlayers() {
        return this.players;
    }

    protected void setupPlayer(Player player) {
        if (player.needsLetters()) {
            player.addLetters(scrabbleAlphabet.getLetters(player.getNumberOfLettersNeeded()));
        }
    }

    public void addReplacedLetterToWord(ScrabbleLetter newWord, int position) {
        scrabbleWord.replaceLetter(newWord, position);
    }

    public void playWord(char[] word, int col, int row, String orientation, char[] blankLetters) {

        scrabbleWord = new ScrabbleWord(word);
        exchangeBlankLetters(blankLetters, scrabbleWord.lettersInWord());
        currentWord = scrabbleWord;

        System.out.println(scrabbleBoard);
         scrabbleBoard.setWordCol(col);
        scrabbleBoard.setWordRow(row);
            scrabbleBoard.setUserSelectedOrientation(orientation);
            scrabbleBoard.getPlayedWords(scrabbleWord, orientation)
            if (playedWordsAreValid()) {
                scrabbleBoard.addWordToBoard(scrabbleWord.lettersInWord(), isFirstRound);
                getWordPointValue();
            }
        currentPlayer.removeLetters(scrabbleWord);
        getLetters();
    }

    private void getWordPointValue() {

        for (List<ScrabbleLetter> word : scrabbleBoard.getWordsToBeChecked()) {
            currentPlayer.getScoreKeeper().getWordPointValue(word, scrabbleBoard.getWordPointValueScaleFactor());
        }
    }

    private boolean playedWordsAreValid() {
        for (List<ScrabbleLetter> scrabbleLetterList : scrabbleBoard.getWordsToBeChecked()) {
            ScrabbleWord wordPlayed = new ScrabbleWord(scrabbleLetterList);

            try {
               if (!isWordInDictionary(wordPlayed.toString())) {
                   return false;
               }

            } catch (ScrabbleGameInvalidWordException e) {
                e.getMessage();
            }
        }
        return true;
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

    private void exchangeBlankLetters(char[] listOfBlankLetters, List<ScrabbleLetter> scrabbleLetterList) {
        for (ScrabbleLetter scrabbleLetter: scrabbleLetterList) {
            if (scrabbleLetter.getLetter() == '_') {
                scrabbleLetter.setLetter(listOfBlankLetters[scrabbleLetterList.indexOf(scrabbleLetter)]);
            }
            System.out.println(scrabbleLetter);
        }
    }

    public void exchangeLetters(char[] lettersToExchange) {
        currentPlayer.getLettersToExchange(lettersToExchange); //userInput.getInputFromUser("Which letters would you like to exchange? ").toUpperCase().toCharArray());
        getLetters();
    }

    private boolean isWordInDictionary(String word) throws ScrabbleGameInvalidWordException {
        Dictionary dictionary = null;
        DictionaryLookupResult result;

        try {
            dictionary = DictionaryFactory.getDictionary();
            result = dictionary.lookupWord(word);
        } catch (ScrabbleGameException e) {
            e.printStackTrace();
            return false;
        }

        return result != null ? result.isValidWord() : false;
    }

    private void skipTurn() {

        if (currentPlayer.getPlayerId() < players.length - 1) {
            currentPlayer = players[currentPlayer.getPlayerId() + 1];
        }
        currentPlayer = players[0];
    }

    protected void startGame() {
    }

    protected void endGame() {

        if (scrabbleAlphabet.getNumberOfLettersLeft() == 0 && currentPlayer.getRack().getLettersOnRack().size() == 0) {
            System.out.println("The Game Has Ended.");

            for (Player currentPlayer : players) {
                System.out.println(currentPlayer.getCurrentPlayerScore());
            }
        }
    }

    /**
     * Load the properties file from the Resources folder.
     * @throws Exception if file cannot be found or loaded
     */
    private void loadConfig() throws ScrabbleGameException {
        ScrabbleGameConfiguration.initialize();
    }
}