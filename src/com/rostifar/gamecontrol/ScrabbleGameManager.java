package com.rostifar.gamecontrol;

import com.rostifar.dictionary.Dictionary;
import com.rostifar.dictionary.DictionaryFactory;
import com.rostifar.dictionary.DictionaryLookupResult;
import com.rostifar.scabbleboard.ScrabbleBoard;
import com.rostifar.scabbleboard.SquareEnum;
import com.rostifar.scrabbleproject.Player;
import com.rostifar.wordDistribution.ScrabbleAlphabet;
import com.rostifar.wordDistribution.ScrabbleGameInvalidWordException;
import com.rostifar.wordDistribution.ScrabbleLetter;
import com.rostifar.wordDistribution.ScrabbleWord;
import org.atmosphere.cpr.Broadcaster;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * Created by Dad on 10/4/2015.
 */
public class ScrabbleGameManager implements Serializable {

    private ScrabbleBoard scrabbleBoard;
    private List<Player> players = new ArrayList<>();
    private Iterator<Player> currentPlayerinInterator = players.iterator();
    private ScrabbleAlphabet scrabbleAlphabet = new ScrabbleAlphabet();
    private Player currentPlayer;
    private ScrabbleWord scrabbleWord;
    private boolean isFirstRound = true;
    private ScrabbleWord currentWord;
    private int pointValueMultiplier;
    private String gameCode;
    private Broadcaster gameBroadcaster;
    private String gameState;
    private boolean newPlayerJoined;
    private boolean gameStateHasChanged;

    /**
     * @ScrabbleGameManager
     * purpose-> setups the ScrabbleGameManager when instantiated
     * */
    public ScrabbleGameManager() throws ScrabbleGameException {
        setupGame();
    }

    /**
     * @setupGame
     * purpose-> begins the setup process by creating the ScrabbleBoard and setting up game infrastructure
     * */
    private void setupGame() throws ScrabbleGameException {
        System.out.println("Setting up Scrabble game...");
        loadConfig();
        scrabbleBoard = new ScrabbleBoard();
        System.out.println(scrabbleBoard);
    }

    public Broadcaster getGameBroadcaster() {
        return gameBroadcaster;
    }

    /**
     * @addPlayers
     * purpose-> adds the appropriate amount of players based on the game lobby size
     */
    public void addPlayer(String playerName, String email) {

        if (playerName == null) {
            playerName = "Player-" + System.currentTimeMillis();
        }

        Player player = new Player(playerName);
        setupPlayer(player);
        players.add(player);

        //Default to first player for now
        currentPlayer = players.get(0);
        printPlayers();
    }

    public void setNewGameState(String newGameState) {
        gameState = newGameState;
    }

    /**
     * @getCurrentPlayer
     * purpose-> returns the current player
     * */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     *@getScrabbleBoard
     * purpose-> gets the current instance of the game ScrabbleBoard
     * */
    public ScrabbleBoard getScrabbleBoard() {
        return scrabbleBoard;
    }

    /**
     * @getPlayers
     * purpose-> returns an array of all the current players in a game instance
     * */
    public List getPlayers() {
        return this.players;
    }

    /**
     * @setupPlayer
     * purpose-> checks whether or not a player needs more letters, refills a player's rack
     * */
    protected void setupPlayer(Player player) {
        if (player.needsLetters()) {
            player.addLetters(scrabbleAlphabet.getLetters(player.getNumberOfLettersNeeded()));
        }
    }

    /**
     *@playWord
     * purpose-> when activated by the servlet, manages the processes which involve playing a word
     * */
    public void playWord(char[] word, List<Integer> col, List<Integer> row, String orientation, char[] blankLetters) {
        List<ScrabbleWord> playedWords;
        scrabbleWord = new ScrabbleWord(word);
        exchangeBlankLetters(blankLetters, scrabbleWord.lettersInWord());
        currentWord = scrabbleWord;
        setUpLettersInWord(scrabbleWord, col, row);

        if (!isFirstRound) {
            playedWords = scrabbleBoard.getPlayedWords(scrabbleWord, orientation);
        } else {
            playedWords = new ArrayList<>();
            playedWords.add(scrabbleWord);
        }

        if (playedWordsAreValid(playedWords)) {
            scrabbleBoard.addWordToBoard(scrabbleWord.lettersInWord());
            currentPlayer.addPointsToPlayerScore(getMovePointValue(playedWords));
            currentPlayer.removeLetters(scrabbleWord);
            System.out.println(currentPlayer.getPlayerScore());
            isFirstRound = false;
            refillRack();
            moveToNextPlayer();
        }
    }

    /**
     * @setUpLettersInWord
     * purpose-> for each letter in a word the method assigns the appropriate row and column
     * */
    private void setUpLettersInWord(ScrabbleWord word, List<Integer> col, List<Integer> row) {
        for (int i = 0; i < word.getNumberOfLetters(); i++) {
            word.getLetterAt(i).setDesiredPositionCol(col.get(i));
            word.getLetterAt(i).setDesiredPositionRow(row.get(i));
        }
    }

    /**
     * @playedWordsAreValid
     * purpose-> checks whether or not all of the words created by a move are valid
     * */
    private boolean playedWordsAreValid(List<ScrabbleWord> playedWords) {
        for (ScrabbleWord word : playedWords) {
            try {
                if (!isWordInDictionary(word.toString())) {
                    return false;
                }
            } catch (ScrabbleGameInvalidWordException e) {}
        }
        return true;
    }

    private String getResponse() {
        return "The Word you have selected is invalid. please try again.";
    }

    private void printPlayers() {
        for (Player playr : players) {
            System.out.println(playr);
        }
    }

    private void moveToNextPlayer() {
        Player lastPlayerInList = players.get(players.size() - 1);
        int currentPlayerIndex = players.indexOf(currentPlayer);
        currentPlayer = (lastPlayerInList == currentPlayer) ? players.get(0) : players.get(currentPlayerIndex + 1);
    }

    private int getMovePointValue(List<ScrabbleWord> playedWords) {
        int totalWordPointValue = 0;
        int wordPointValue = 0;

        for (ScrabbleWord word: playedWords) {
            pointValueMultiplier = 0;
            for (ScrabbleLetter letter : word.lettersInWord()) {
                if (scrabbleBoard.getSquarePosition(letter.getDesiredPositionCol(), letter.getDesiredPositionRow()).isSpecialSquare()) {
                    specialSquarePointValue(scrabbleBoard.getSquarePosition(letter.getDesiredPositionCol(), letter.getDesiredPositionRow()).getSquareType(), letter);
                }
                wordPointValue += letter.getPointValue().getValue();
            }
            if (pointValueMultiplier != 0) {
                wordPointValue += wordPointValue * pointValueMultiplier;
            }
            totalWordPointValue += wordPointValue;
            wordPointValue = 0;
        }
        return totalWordPointValue;
    }

    private void specialSquarePointValue(SquareEnum squareType, ScrabbleLetter letter) {
        switch (squareType) {
            case DOUBLE_LETTER:
                letter.setNewPointValue(letter.getPointValue().getValue() * 2);
                break;
            case TRIPLE_LETTER:
                letter.setNewPointValue(letter.getPointValue().getValue() * 3);
                break;
            case DOUBLE_WORD:
                pointValueMultiplier += 2;
                break;
            case TRIPLE_WORD:
                pointValueMultiplier += 3;
                break;
        }
    }

    private void refillRack() {
        if (currentPlayer.needsLetters() && scrabbleAlphabet.getNumberOfLettersLeft() > 6) {
            currentPlayer.addLetters(scrabbleAlphabet.getLetters(currentPlayer.getNumberOfLettersNeeded()));
        }
        else if(currentPlayer.needsLetters() && scrabbleAlphabet.getNumberOfLettersLeft() < 7) {
            currentPlayer.addLetters(scrabbleAlphabet.getLetters(scrabbleAlphabet.getNumberOfLettersLeft()));
        }
        if (currentPlayer.getRack().getLettersOnRack().size() == 0) {
            endGame();
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

    private void exchangeLetters(char[] lettersToExchange) {
        currentPlayer.getRack().exchangeLetters(lettersToExchange);
        isFirstRound = false;
        refillRack();
        moveToNextPlayer();
    }

    private void skipTurn() {
        isFirstRound = false;
        moveToNextPlayer();
    }

    /**
     * Load the properties file from the Resources folder.
     * @throws Exception if file cannot be found or loaded
     */
    private void loadConfig() throws ScrabbleGameException {
        ScrabbleGameConfiguration.initialize();
    }

    private void endGame() {}
}