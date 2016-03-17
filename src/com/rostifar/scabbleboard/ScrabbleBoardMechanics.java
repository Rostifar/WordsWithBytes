package com.rostifar.scabbleboard;
import com.rostifar.wordDistrobution.ScrabbleLetter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by ross on 1/16/16.
 */
public class ScrabbleBoardMechanics {

    private ScrabbleBoard scrabbleBoard;
    private int col;
    private int row;
    private String orientation;
    private List<ScrabbleLetter> horzAdditiveConnectedWord = new ArrayList<>();
    private List<ScrabbleLetter> horzSubtractiveConnectedWord = new ArrayList<>();
    private List<ScrabbleLetter> vertAdditiveConnectedWord = new ArrayList<>();
    private List<ScrabbleLetter> vertSubtractiveConnectedWord = new ArrayList<>();
    private List<ScrabbleLetter> secondaryWord;
    private List<ScrabbleLetter> mainWord;
    private boolean forward = true;
    private boolean up = true;
    private boolean foundConnectingWords;
    private ScrabbleLetter currentLetter;
    private Square initalPosition;
    private List<ScrabbleLetter> secondaryWordToCheck = new ArrayList<>();
    private List<ScrabbleLetter> primaryWordToCheck = new ArrayList<>();
    private List<ScrabbleLetter> playedWord;

    public ScrabbleBoardMechanics(ScrabbleBoard scrabbleBoard) {
        this.scrabbleBoard = scrabbleBoard;
        setCol(scrabbleBoard.getWordColumn());
        setRow(scrabbleBoard.getWordRow());
        setOrientation(scrabbleBoard.getWordOrientation());
    }

    public void setCol(int col) {
        this.col = col;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setOrientation(String orientation) {
        this.orientation = orientation;
    }

    public void connectSecondaryWord() {

        if (orientation.equals("v")) {

            Collections.reverse(horzSubtractiveConnectedWord);
            secondaryWordToCheck.addAll(horzSubtractiveConnectedWord);
            secondaryWordToCheck.add(currentLetter);
            secondaryWordToCheck.addAll(horzAdditiveConnectedWord);
        }

        if (orientation.equals("h")) {
            Collections.reverse(vertSubtractiveConnectedWord);
            secondaryWordToCheck.addAll(vertSubtractiveConnectedWord);
            secondaryWordToCheck.add(currentLetter);
            secondaryWordToCheck.addAll(vertAdditiveConnectedWord);
        }
    }

    public void connectMainWord() {

        if (orientation.equals("v") ) {

            Collections.reverse(vertSubtractiveConnectedWord);
            mainWord.addAll(vertSubtractiveConnectedWord);
            mainWord.add(currentLetter);
            mainWord.addAll(vertAdditiveConnectedWord);
        }
        else if (orientation.equals("h")) {

            Collections.reverse(horzSubtractiveConnectedWord);
            primaryWordToCheck.addAll(horzSubtractiveConnectedWord);
            primaryWordToCheck.add(currentLetter);
            primaryWordToCheck.addAll(horzSubtractiveConnectedWord);
        }

    }

    public void checkForConnectingWords() {
        checkForFirstLetter();
        connectMainWord();
        connectSecondaryWord();
        clearPreviouslyFoundLetters();

    }

    public List<ScrabbleLetter> getMainWord() {return primaryWordToCheck;}

    public List<ScrabbleLetter> getSecondaryWord() {return secondaryWordToCheck;}

    public void checkForFirstLetter() {

        int column = this.col;
        int rowNumber = this.row;
        boolean forward = this.forward;
        boolean up = this.up;

        while (up && forward) {
            if (scrabbleBoard.getSquarePosition(column, rowNumber + 1).containsLetter()) {
                rowNumber = rowNumber + 1;
                horzAdditiveConnectedWord.add(scrabbleBoard.getSquarePosition(column, rowNumber).getLetter());
                expandWordSearch(col, rowNumber, true, horzAdditiveConnectedWord);
                rowNumber = row;
            }
            if (scrabbleBoard.getSquarePosition(column + 1, rowNumber).containsLetter()) {
                column = column + 1;
                vertAdditiveConnectedWord.add(scrabbleBoard.getSquarePosition(column, rowNumber).getLetter());
                expandWordSearch(column, rowNumber, true, vertAdditiveConnectedWord);
                column = col;
            }
            up = false;
            forward = false;
        }

        while (!up && !forward) {
            if (scrabbleBoard.getSquarePosition(column, rowNumber - 1).containsLetter() && (orientation.equals("v") || scrabbleBoard.getSquarePosition(column, rowNumber) == initalPosition)) {
                rowNumber = rowNumber - 1;
                horzSubtractiveConnectedWord.add(scrabbleBoard.getSquarePosition(column, rowNumber).getLetter());
                expandWordSearch(column, rowNumber, false, horzSubtractiveConnectedWord);
                rowNumber = row;
            }

            if (scrabbleBoard.getSquarePosition(column - 1, rowNumber).containsLetter() && (orientation.equals("h") || scrabbleBoard.getSquarePosition(column, rowNumber) == initalPosition)) {
                column = column - 1;
                vertSubtractiveConnectedWord.add(scrabbleBoard.getSquarePosition(column, rowNumber).getLetter());
                expandWordSearch(column, rowNumber, false, vertSubtractiveConnectedWord);
                column = col;
            } else {
                return;
            }
        }
        foundConnectingWords = false;
    }

    public void expandWordSearch(int col, int row, boolean direction, List<ScrabbleLetter> selectedList) {
        int defaultCol = col;
        int defaultRow = row;
        foundConnectingWords = true;

        if (direction) {
            while (scrabbleBoard.getSquarePosition(col, ++row).containsLetter() && selectedList == horzAdditiveConnectedWord) {
                horzAdditiveConnectedWord.add(scrabbleBoard.getSquarePosition(col, row).getLetter());
            }
            row = defaultRow;
            while (scrabbleBoard.getSquarePosition(++col, row).containsLetter() && selectedList == vertAdditiveConnectedWord) {
                vertAdditiveConnectedWord.add(scrabbleBoard.getSquarePosition(col, row).getLetter());
            }
            col = defaultCol;
        }

        if (!direction) {
            while (scrabbleBoard.getSquarePosition(col, --row).containsLetter() && selectedList == horzSubtractiveConnectedWord) {
                horzSubtractiveConnectedWord.add(scrabbleBoard.getSquarePosition(col, row).getLetter());
            }
            row = defaultRow;
            while (scrabbleBoard.getSquarePosition(--col, row).containsLetter() && selectedList == vertSubtractiveConnectedWord) {
                vertSubtractiveConnectedWord.add(scrabbleBoard.getSquarePosition(col, row).getLetter());
            }
        }
    }


    public void getCurrentLetter(ScrabbleLetter scrabbleLetter) {
        this.currentLetter = scrabbleLetter;
    }

    public void getInitalPostion(Square initalPosition) {
        this.initalPosition = initalPosition;
    }

    public List<ScrabbleLetter> getHorizontalNegative() {
        return horzSubtractiveConnectedWord;
    }

    public List<ScrabbleLetter> getHorizontalPositive() {
        return horzAdditiveConnectedWord;
    }

    public List<ScrabbleLetter> getVerticalNegative() {
        return vertSubtractiveConnectedWord;
    }

    public List<ScrabbleLetter> getVerticalPositive() {
        return vertAdditiveConnectedWord;
    }

    private void clearPreviouslyFoundLetters() {
        horzAdditiveConnectedWord.clear();
        horzSubtractiveConnectedWord.clear();
        vertAdditiveConnectedWord.clear();
        vertSubtractiveConnectedWord.clear();
    }

    public void getPlayedWord(List<ScrabbleLetter> playedWord) {
        this.playedWord = playedWord;
    }

    public boolean isConnectedToPreviousWord(boolean isFirstTurn) {

        if (/*isEmpty() && */!isFirstTurn) {
            new NonConnectingPlayException().getMessage();
            return false;
        }
        return true;
    }
}



