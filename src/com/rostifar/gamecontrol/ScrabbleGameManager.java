package com.rostifar.gamecontrol;

import com.rostifar.dictionary.Dictionary;
import com.rostifar.dictionary.DictionaryFactory;
import com.rostifar.dictionary.DictionaryLookupResult;
import com.rostifar.scabbleboard.ScrabbleBoard;
import com.rostifar.scrabbleproject.Player;
import com.rostifar.scrabbleproject.Rack;
import com.rostifar.wordDistrobution.*;

import java.io.Serializable;
import java.util.List;


/**
 * Created by Dad on 10/4/2015.
 */
public class ScrabbleGameManager implements Serializable {

    private ScrabbleBoard scrabbleBoard;
    private Player players[];
    //private UserInput userInput;
    private ScrabbleAlphabet scrabbleAlphabet = new ScrabbleAlphabet();
    private Player currentPlayer;
    private Rack playerRack;
    private ScrabbleWord scrabbleWord;
    private boolean isFirstRound = true;
    private ScrabbleWord currentWord;
    private ScrabbleLetter currentLetter;


    public ScrabbleGameManager() throws ScrabbleGameException {
       // userInput = new UserInput();
        setupGame();
    }

    @Deprecated
    public void runGame() {
        startGame();
        endGame();
    }

    private void setupGame() throws ScrabbleGameException {
        System.out.println("Setting up Scrabble game...");
        loadConfig();
        scrabbleBoard = new ScrabbleBoard();
        System.out.println(scrabbleBoard);
        //addPlayers(2);
       // runGame();
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

    protected void setupPlayer(Player player) {
        if (player.needsLetters()) {
            player.addLetters(scrabbleAlphabet.getLetters(player.getNumberOfLettersNeeded()));
        }
    }

    private void isWordOnRack(ScrabbleWord scrabbleWord) {
        if (!currentPlayer.isValidWord(scrabbleWord)) {
            System.out.println("Error! You do not have the letters you have selected on your Rack. Please play another word.");
            //FIXME: return error to user
            //makeMove("p");
        }
    }

    private void evaluateBlankLetters() {
        List<ScrabbleLetter> blankLetters = currentWord.getBlankScrabbleLetters();
        List<Integer> positions = currentWord.getBlankScrabbleLetterPostion();

        for (ScrabbleLetter blankLetter: blankLetters) {
            exchangeBlankLetter(blankLetter, positions.get(blankLetters.indexOf(blankLetter)));
        }
    }

    public void exchangeBlankLetter(ScrabbleLetter blankLetter, int position) {
        System.out.println(scrabbleAlphabet.getListOfLetters());
        char selectedLetter = ' '; //userInput.getInputFromUser("The word you have played contains a blank letter. Please select the letter you would like to exchange it for: ").toUpperCase().charAt(0);
        ScrabbleLetter newScrabbleLetter = new BlankScrabbleLetter(selectedLetter);
        currentPlayer.getRack().replaceBlankLetter(newScrabbleLetter, blankLetter);
        addReplacedLetterToWord(newScrabbleLetter, position);

    }

    public void addReplacedLetterToWord(ScrabbleLetter newWord, int position) {
        scrabbleWord.replaceLetter(newWord, position);
    }

    public void playWord(String word, int col, int row, String orientation) {

        scrabbleWord = new ScrabbleWord(word);
        currentWord = scrabbleWord;
        scrabbleWord.searchForBlankLetter();

        if (!scrabbleWord.getBlankScrabbleLetters().isEmpty()) {
            evaluateBlankLetters();
        }

        isWordOnRack(scrabbleWord);
//        validateWord(scrabbleWord);
        System.out.println(scrabbleBoard);
        /*int col = Integer.parseInt(userInput.getInputFromUser("At what column would you like to place your selected word ? "));
        int row = Integer.parseInt(userInput.getInputFromUser("At what row would you like to place your selected word ? "));*/
       // String orientation = userInput.getInputFromUser("Would you like your selected word to go horizontal or vertical ? (ie. v or h)");

        if (scrabbleBoard.squareContainsLetter(col, row)) {
            System.out.println("Error the location you have selected has been already used. ");
            //FIXME: return error to user
            //makeMove("p");
            currentPlayer.getRack();
        } else {
            scrabbleBoard.setWordCol(col);
            scrabbleBoard.setWordRow(row);
            scrabbleBoard.setUserSelectedOrientation(orientation);
            scrabbleBoard.getScrabbleBoardInstance(scrabbleBoard);
            scrabbleBoard.calculateMovePointValue();
            scrabbleBoard.validateWordPlacement(scrabbleWord.lettersInWord());
            if (playedWordsAreValid()) {
                scrabbleBoard.addWordToBoard(scrabbleWord.lettersInWord(), isFirstRound);
                getWordPointValue();
            }
            //isPlacementValid();
            System.out.println(scrabbleBoard);
            System.out.println(currentPlayer.getCurrentPlayerScore());
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
                   //FIXME: return error to user
                 //  makeMove("p");
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

    @Deprecated
    private void makeMove(String moveSelected) {

        //String moveSelected;
        boolean takingTurn = true;

        while (takingTurn) {

         /*   String input = userInput.getInputFromUser("Make a move(s = skip turn, p = play word, e = exchange letters): ");
            moveSelected = String.valueOf(input);
*/
            if (!isValidInput(moveSelected)) {
                System.out.println("invalid entry, try again");
                continue;
            }

            switch (moveSelected) {

                case ("p"):
                    playWord("test", 1, 2, "v");

                    takingTurn = false;
                    isFirstRound = false;
                    break;
                case ("s"):

                    takingTurn = false;
                    break;
                case ("e"):
                    char[] ex = {'a', 'c'};
                    exchangeLetters(ex);
                    takingTurn = false;
                    break;
                case ("quit"):

                    makeMove("p");
                    break;
                default:
                    break;
            }


        }
    }


   /* @Deprecated
    private void runGame() {

        for (int currntIdx = 0; currntIdx < players.length; currntIdx++) {

            currentPlayer = players[currntIdx];
            playerRack = currentPlayer.getRack();

            System.out.println("\n");
            System.out.println(currentPlayer);
            makeMove("p");
            if (currntIdx == players.length - 1) {
                currntIdx = -1;
            }
        }

    }*/


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