package com.rostifar.scabbleboard;

import com.rostifar.gamecontrol.ScrabbleGameException;
import com.rostifar.wordDistribution.ScrabbleLetter;

import java.io.Serializable;
import java.util.ArrayList;
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
    public static final int ROW_LENGTH = 15;
    public static final int COLUMN_LENGTH = 15;
    public static final int CENTER_SQUARE = 7;
    private int col;
    private int row;
    private String orientation;
    private ScrabbleBoardMechanics scrabbleBoardMechanics;
    private int wordScoreMultiplier;
    private boolean isValidWordPlacement;
    private List<List<ScrabbleLetter>> wordsToBeChecked = new ArrayList<>();

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
        //Row 0, 7 and 14 are the same
        for (int row = 0; row <= ROW_LENGTH - 1; row += 7) {
            board[row][3] = new Square(SquareEnum.DOUBLE_LETTER);
            board[row][11] = new Square(SquareEnum.DOUBLE_LETTER);
        }

        //Row 2 and row 12 are the same
        for (int row = 2; row <= 12; row += 10) {
            board[row][6] = new Square(SquareEnum.DOUBLE_LETTER);
            board[row][8] = new Square(SquareEnum.DOUBLE_LETTER);
        }

        //Row 3 and row 11 are the same
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

    /**
     * @return true if the square located at col,row on the board contains a letter, otherwise false
     */

    public void getScrabbleBoardInstance(ScrabbleBoard scrabbleBoard) {
        scrabbleBoardMechanics = new ScrabbleBoardMechanics(scrabbleBoard);
    }

    public boolean squareContainsLetter(int col, int row) {
        return board[col][row].containsLetter();
    }

    public void setUserSelectedOrientation(String orientation) {
        this.orientation = orientation;
    }

    public int setWordRow(int row) {
        return this.row = row;
    }

    public int setWordCol(int col) {
        return this.col = col;
    }

    public int getWordColumn() {
        return col;
    }

    public int getWordRow() {
        return row;
    }

    public String getWordOrientation() {
        return orientation;
    }

    public Square getSquarePosition(int col, int row){
        return board[col][row];
    }


    /**
     * Add a letter to an empty square on the scrabble board.
     * @throws ScrabbleGameException if square is not empty. This should not happen if the caller utilizes
     * the squareContainsLetter() as a prerequisite to calling this  method.
     */

    private void isIntersectingWord(boolean isFirstRound) { 
        if (!scrabbleBoardMechanics.isConnectedToPreviousWord(isFirstRound)) {
            isValidWordPlacement = false;
        }
    }

    public void validateWordPlacement(List<ScrabbleLetter> selectedLetters) {
        wordScoreMultiplier = 0;
        scrabbleBoardMechanics.getInitalPostion(getSquarePosition(col, row));

        for (ScrabbleLetter scrabbleLetter : selectedLetters) {
            determinePreviouslyPlacedLetters();
            checkForNearbyWords(scrabbleLetter);
            getSecondaryWord();
            getLetterCoordinates(scrabbleLetter);
          //  scrabbleBoardMechanics.clearSecondaryWord();
        }
        getPrimaryWord();
    }

    private void getLetterCoordinates(ScrabbleLetter currentLetter) {

        switch (getWordOrientation()) {

            case "v":
                currentLetter.setDesiredPositionCol(col++);
                currentLetter.setDesiredPositionRow(row);
                break;
            case "h":
                currentLetter.setDesiredPositionCol(col);
                currentLetter.setDesiredPositionRow(row++);
                break;
        }
    }

    public void calculateMovePointValue() {
        for (List<ScrabbleLetter> word : wordsToBeChecked) {

            for (ScrabbleLetter currentLetter : word) {

                if (board[currentLetter.getDesiredPositionCol()][currentLetter.getDesiredPositionRow()].isSpecialSquare()) {
                    calculateSpecialPointValue(currentLetter, board[currentLetter.getDesiredPositionCol()][currentLetter.getDesiredPositionRow()].getSquareType());
                }
            }
        }
    }

    public void addWordToBoard(List<ScrabbleLetter> lettersToAdd, boolean isFirstRound) {

        for (ScrabbleLetter currentLetter : lettersToAdd) {
            try {
                addLetterToSquare(currentLetter, currentLetter.getDesiredPositionCol(), currentLetter.getDesiredPositionRow());
            } catch (ScrabbleGameException e) {
                e.getMessage();
            }
        }
        //isIntersectingWord(isFirstRound);
    }

    public int getWordPointValueScaleFactor() {
        return wordScoreMultiplier;
    }

    private void checkForNearbyWords(ScrabbleLetter scrabbleLetter) {
        scrabbleBoardMechanics.getCurrentLetter(scrabbleLetter);
        scrabbleBoardMechanics.checkForConnectingWords();
    }

    private void getSecondaryWord() {

        if (!scrabbleBoardMechanics.getSecondaryWord().isEmpty()) {
            wordsToBeChecked.add(scrabbleBoardMechanics.getSecondaryWord());
        }
    }

    private void getPrimaryWord() {
        if (!scrabbleBoardMechanics.getMainWord().isEmpty()) {
            wordsToBeChecked.add(scrabbleBoardMechanics.getMainWord());
        }
    }

    public List<List<ScrabbleLetter>> getWordsToBeChecked() {
        return wordsToBeChecked;
    }


    public void addLetterToSquare(ScrabbleLetter letterToAdd, int col, int row) throws ScrabbleGameException {
        board[col][row].setLetter(letterToAdd);
    }

    public void calculateSpecialPointValue(ScrabbleLetter scrabbleLetter, SquareEnum squareType) {

        switch (squareType) {

        case DOUBLE_LETTER:
            scrabbleLetter.getPointValue().setNewPointValue(2);
            break;
        case TRIPLE_LETTER:
            scrabbleLetter.getPointValue().setNewPointValue(3);
            break;
        case DOUBLE_WORD:
            wordScoreMultiplier += 2;
            break;
        case TRIPLE_WORD:
            wordScoreMultiplier += 3;
        default:
            break;
        }
    }


    public void determinePreviouslyPlacedLetters() {

        if (squareContainsLetter(col,row) && orientation.equals("v")) {
            col = col + 1;
        } else if (squareContainsLetter(col,row) && orientation.equals("h")){
            row = row + 1;
        }
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

    public boolean getIsValidWordPlacement() {
        return isValidWordPlacement;
    }

}