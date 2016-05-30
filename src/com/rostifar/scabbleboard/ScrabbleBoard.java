package com.rostifar.scabbleboard;

import com.rostifar.gamecontrol.ScrabbleGameException;
import com.rostifar.wordDistribution.ScrabbleLetter;
import com.rostifar.wordDistribution.ScrabbleWord;
import org.atmosphere.cpr.AtmosphereHandler;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Dad on 10/4/2015.
 * Example of the board layout which will show with call to toString()
 *
 * 	  1  2  3  4  5  6  7  8  9 10 11 12 13 14 15
 ----------------------------------------------
 1	|TW|  |  |DL|  |  |  |TW|  |  |  |DL|  |  |TW|
 2	|  |DW|  |  |  |TL|  |  |  |TL|  |  |  |TL|  |
 3	|  |  |DW|  |  |  |DL|  |DL|  |  |  |DW|  |  |
 4	|DL|  |  |DW|  |  |  |DL|  |  |  |DW|  |  |DL|
 5	|  |  |  |  |DW|  |  |  |  |  |DW|  |  |  |  |
 6	|  |TL|  |  |  |TL|  |  |  |TL|  |  |  |TL|  |
 7	|  |  |DL|  |  |  |DL|  |DL|  |  |  |DL|  |  |
 8	|TW|  |  |DL|  |  |  | *|  |  |  |DL|  |  |TW|
 9	|  |  |DL|  |  |  |DL|  |DL|  |  |  |DL|  |  |
 10	|  |TL|  |  |  |TL|  |  |  |TL|  |  |  |TL|  |
 11	|  |  |  |  |DW|  |  |  |  |  |DW|  |  |  |  |
 12	|DL|  |  |DW|  |  |  |DL|  |  |  |DW|  |  |DL|
 13	|  |  |DW|  |  |  |DL|  |DL|  |  |  |DW|  |  |
 14	|  |DW|  |  |  |TL|  |  |  |TL|  |  |  |TL|  |
 15	|TW|  |  |DL|  |  |  |TW|  |  |  |DL|  |  |TW|
 ----------------------------------------------
 /**
 * A good online reference for the board and the game can be found here:
 * @see  /
 */
public class ScrabbleBoard implements Serializable {
    private static final int ROW_LENGTH = 15;
    private static final int COLUMN_LENGTH = 15;
    private static final int CENTER_SQUARE = 7;
    private List<ScrabbleWord> playedWords;
    private List<ScrabbleLetter> currentMainWord;
    private List<ScrabbleLetter> currentSecondaryWord;
    private boolean isFirstLetter = true;
    private String orientation;
    private ScrabbleWord currentWord;

    private Square[][] board = new Square[COLUMN_LENGTH][ROW_LENGTH];

    public ScrabbleBoard() {
        setupBoard(); //Setup the board on instantiation
    }

    private void setupBoard() {
        setupDoubleWords();
        setupTripleWords();
        setupDoubleLetters();
        setupTripleLetters();
        setupCenterSquare();
        setupDefaultSquares();
    }

    /**
     *  Set any square not already assigned to a plain regular space
     */
    private void setupDefaultSquares() {

        for (int col = 0; col < COLUMN_LENGTH; col++) {
            for (int row = 0; row < ROW_LENGTH; row++) {
                if (board[col][row] == null)
                    board[col][row] = new Square(SquareEnum.REGULAR);
            }
        }
    }


    private void setupCenterSquare() {
        board[CENTER_SQUARE][CENTER_SQUARE] =  new Square(SquareEnum.CENTER_STAR);
    }

    private void setupDoubleWords() {
        for (int row =  1, col = 1;  row < ROW_LENGTH; row++, col++) {

            if (row >= 5 && row <= 8)
                continue; //Skip middle of board per board layout

            board[row][col] = new Square(SquareEnum.DOUBLE_WORD);
            board[row][(COLUMN_LENGTH  - 1) - col] = new Square(SquareEnum.DOUBLE_WORD);
        }
    }

    private void setupTripleWords() {

        for (int row = 0; row <= ROW_LENGTH - 1; row += 7) {
            for (int col = 0; col < ROW_LENGTH; col += 7) {
                if (row == CENTER_SQUARE && col == CENTER_SQUARE) //Skip over center square
                    continue;
                board[row][col] = new Square(SquareEnum.TRIPLE_WORD);
            }
        }
    }

    private void setupDoubleLetters() {
        for (int row = 0; row <= ROW_LENGTH - 1; row += 7) {
            board[row][3] = new Square(SquareEnum.DOUBLE_LETTER);
            board[row][11] = new Square(SquareEnum.DOUBLE_LETTER);
        }

        for (int row = 2; row <= 12; row += 10) {
            board[row][6] = new Square(SquareEnum.DOUBLE_LETTER);
            board[row][8] = new Square(SquareEnum.DOUBLE_LETTER);
        }

        for (int row = 3; row <= 12; row += 8) {
            for (int col = 0; col < COLUMN_LENGTH; col += 7) {
                board[row][col] = new Square(SquareEnum.DOUBLE_LETTER);
            }
        }

        //Row 6 and row 8 are the same
        //TODO: factor out the column indices
        for (int row = 6; row <= 8; row += 2) {
            board[row][2] = new Square(SquareEnum.DOUBLE_LETTER);
            board[row][6] = new Square(SquareEnum.DOUBLE_LETTER);
            board[row][8] = new Square(SquareEnum.DOUBLE_LETTER);
            board[row][12] = new Square(SquareEnum.DOUBLE_LETTER);
        }
    }

    private void setupTripleLetters() {

        for (int row = 5; row <= 10; row += 4) {
            for (int col = 1; col < COLUMN_LENGTH; col += 4) {
                board[row][col] = new Square(SquareEnum.TRIPLE_LETTER);
            }
        }

        for (int row = 1; row <= ROW_LENGTH; row += 12) {
            for (int col = 5; col < COLUMN_LENGTH; col += 4) {
                board[row][col] = new Square(SquareEnum.TRIPLE_LETTER);
            }
        }

    }

    public boolean squareContainsLetter(int col, int row) {
        return board[col][row].containsLetter();
    }


    public Square getSquarePosition(int col, int row){
        return board[col][row];
    }


    /**
     * @foundLetter
     * purpose-> calculates whether or not the board holds a letter at a certain position
     * */
    private boolean foundLetter(int orgPos, int constPos) {
        return orientation.equals("horizontal") ? board[orgPos][constPos].containsLetter() : board[constPos][orgPos].containsLetter();
    }

    /**
     * @getLetter
     * purpose-> gets the letter a certain coordinate on the board, if there is one available
     * */
    private ScrabbleLetter getLetter(int orgPos, int constPos) {
        return orientation.equals("horizontal") ? board[orgPos][constPos].getLetter() : board[constPos][orgPos].getLetter();
    }

    /**
     * @searchForMainWord
     * purpose-> finds the letters which connect the main word together, the main word consists of all of the letters which
     * reside in a selected orientation
     * */
    private void searchForMainWord(int orgPosition, int constPosition, boolean isFirstLetter, ScrabbleLetter letter) throws ArrayIndexOutOfBoundsException {
        List<ScrabbleLetter> backLetter = new ArrayList<>();
        List<ScrabbleLetter> frontLetter = new ArrayList<>();
        currentMainWord = new ArrayList<>();
        int posToTest = orgPosition;


        if (isFirstLetter) {
            while (foundLetter(posToTest--, constPosition)) {
                try {
                    backLetter.add(getLetter(posToTest, constPosition));
                } catch(IndexOutOfBoundsException e){}
            }
            Collections.reverse(backLetter);
            currentMainWord.addAll(backLetter);
        }
        posToTest = orgPosition;

        while (foundLetter(posToTest++, constPosition)) {
            try {
                frontLetter.add(getLetter(posToTest, constPosition));
            } catch (IndexOutOfBoundsException e) {}
        }
        currentMainWord.add(letter);
        currentMainWord.addAll(frontLetter);
    }
    /**
     * @searchForSecondaryWord
     * purpose-> finds words which may exist opposite of the selected word orientation
     * */
    private void searchForSecondaryWord(int orgPosition, int constPosition, ScrabbleLetter letter) throws ArrayIndexOutOfBoundsException {
        List<ScrabbleLetter> backLetters = new ArrayList<>();
        List<ScrabbleLetter> frontLetters = new ArrayList<>();
        currentSecondaryWord = new ArrayList<>();
        int posToTest = constPosition;

        while(foundLetter(orgPosition, posToTest--)) {
            try {
                backLetters.add(getLetter(orgPosition, posToTest));
            } catch (IndexOutOfBoundsException e) {}
        }
        posToTest = constPosition;

        while(foundLetter(orgPosition, posToTest++)) {
            try {
                frontLetters.add(getLetter(orgPosition, posToTest));
            } catch (IndexOutOfBoundsException e) {}
        }
            Collections.reverse(backLetters);
            currentSecondaryWord.addAll(backLetters);
            currentSecondaryWord.add(letter);
            currentSecondaryWord.addAll(frontLetters);
            ScrabbleWord secondaryWord = new ScrabbleWord(currentSecondaryWord);
            playedWords.add(secondaryWord);
    }

    private void searchForWords(ScrabbleLetter currentLetter, boolean isFirstLetter) {
        int orgPosition = (orientation.equals( "horizontal") ? currentLetter.getDesiredPositionCol() : currentLetter.getDesiredPositionRow());
        int constPosition = (orientation.equals("horizontal") ? currentLetter.getDesiredPositionRow() : currentLetter.getDesiredPositionCol());
        searchForMainWord(orgPosition, constPosition, isFirstLetter, currentLetter);
        searchForSecondaryWord(orgPosition, constPosition, currentLetter);
    }

    /**
     * @getPlayedWords
     * purpose-> connects all of the words which are made through the playing of a word, residing of both
     * previously played letters and current letters*/
    public List<ScrabbleWord> getPlayedWords(ScrabbleWord word, String wordOrientation) {
        playedWords = new ArrayList<>();
        orientation = wordOrientation;
        currentWord = word;

        for (ScrabbleLetter letter : word.lettersInWord()) {
            searchForWords(letter, isFirstLetter);
            isFirstLetter = false;
        }
        ScrabbleWord currentWord = new ScrabbleWord(currentMainWord);
        playedWords.add(currentWord);
        return playedWords;
    }

    public void addWordToBoard(List<ScrabbleLetter> lettersToAdd, boolean isFirstRound) {

        for (ScrabbleLetter currentLetter : lettersToAdd) {
            try {
                addLetterToSquare(currentLetter, currentLetter.getDesiredPositionCol(), currentLetter.getDesiredPositionRow());
            } catch (ScrabbleGameException e) {
                e.getMessage();
            }
        }
    }

    private void addLetterToSquare(ScrabbleLetter letterToAdd, int col, int row) throws ScrabbleGameException {
        board[col][row].setLetter(letterToAdd);
    }

    @Override
    /**
     * Print the board and it's current contents to console using basic characters
     */
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\t  0  1  2  3  4  5  6  7  8  9 10 11 12 13 14\n");
        stringBuilder.append("\t----------------------------------------------\n");

        for (int col = 0; col < COLUMN_LENGTH; col++) {
            stringBuilder.append(col).append("\t|");

            for (int row = 0; row < ROW_LENGTH; row++) {
                stringBuilder.append(board[col][row]).append('|');
            }

            stringBuilder.append("\n");
        }

        stringBuilder.append("\t----------------------------------------------\n");

        return stringBuilder.toString();
    }

}