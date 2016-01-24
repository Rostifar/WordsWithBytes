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

    public List<ScrabbleLetter> getSecondaryWord() {
        if (foundConnectingWords) {
            connectFoundParallelWords();
            return secondaryWordToCheck;
        }
        secondaryWordToCheck.clear();
        return secondaryWordToCheck;
    }

    public List<ScrabbleLetter> getPrimaryWord() {
        connectMainWord();
        return primaryWordToCheck;
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

    public void connectFoundParallelWords() {

        if (orientation.equals("v")) {
            Collections.reverse(horzSubtractiveConnectedWord);
            horzSubtractiveConnectedWord.add(currentLetter);
            horzSubtractiveConnectedWord.addAll(horzAdditiveConnectedWord);
            secondaryWordToCheck.addAll(horzSubtractiveConnectedWord);
            clearExcessHorizontalWords();
        }

        if (orientation.equals("h")) {
            Collections.reverse(vertSubtractiveConnectedWord);
            vertSubtractiveConnectedWord.add(currentLetter);
            vertSubtractiveConnectedWord.addAll(vertAdditiveConnectedWord);
            secondaryWordToCheck.addAll(vertSubtractiveConnectedWord);
            clearExcessVerticalWords();
        }
    }

    public void connectMainWord() {

        if (orientation.equals("v")) {

            for(ScrabbleLetter scrabbleLetter : playedWord) {
                vertSubtractiveConnectedWord.add(scrabbleLetter);
            }
            vertSubtractiveConnectedWord.addAll(vertAdditiveConnectedWord);
            primaryWordToCheck = vertSubtractiveConnectedWord;
            clearExcessVerticalWords();
        }

        if (orientation.equals("h")) {

            for(ScrabbleLetter scrabbleLetter : playedWord) {
                horzSubtractiveConnectedWord.add(scrabbleLetter);
            }
            horzSubtractiveConnectedWord.addAll(horzAdditiveConnectedWord);
            primaryWordToCheck = horzSubtractiveConnectedWord;
            clearExcessHorizontalWords();
        }
    }

    public void checkForConnectingWords() {
        checkForFirstLetter();
    }

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

    private void clearExcessHorizontalWords() {
        horzAdditiveConnectedWord.clear();
        horzSubtractiveConnectedWord.clear();
    }

    private void clearExcessVerticalWords() {
        vertAdditiveConnectedWord.clear();
        vertSubtractiveConnectedWord.clear();
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

    public void getPlayedWord(List<ScrabbleLetter> playedWord) {
        this.playedWord = playedWord;
    }
}



