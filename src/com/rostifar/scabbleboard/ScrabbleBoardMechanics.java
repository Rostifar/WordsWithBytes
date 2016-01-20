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
    private List<ScrabbleLetter> wordToCheck = new ArrayList<>();

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

    public void connectFoundParallelWords() {

        if (orientation.equals("v")) {
            Collections.reverse(horzSubtractiveConnectedWord);
            horzSubtractiveConnectedWord.add(currentLetter);
            horzSubtractiveConnectedWord.addAll(horzAdditiveConnectedWord);
            wordToCheck.addAll(horzSubtractiveConnectedWord);
        }

        if (orientation.equals("h")) {
            Collections.reverse(vertSubtractiveConnectedWord);
            vertSubtractiveConnectedWord.add(currentLetter);
            vertSubtractiveConnectedWord.addAll(vertAdditiveConnectedWord);
            wordToCheck.addAll(vertSubtractiveConnectedWord);
        }
    }

    public void connectMainWord() {

    }

    public boolean isInitalPosition() {
        return initalPosition == scrabbleBoard.getSquarePosition(col, row);
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
                int newRowNumber = rowNumber;
                horzAdditiveConnectedWord.add(scrabbleBoard.getSquarePosition(column, newRowNumber).getLetter());
                expandWordSearch(col, newRowNumber, true);
                rowNumber = row;
            }
            if (scrabbleBoard.getSquarePosition(column + 1, rowNumber).containsLetter()) {
                int newColNumber = column;
                vertAdditiveConnectedWord.add(scrabbleBoard.getSquarePosition(newColNumber, rowNumber).getLetter());
                expandWordSearch(newColNumber, rowNumber, true);
                column = col;
            }
            up = false;
            forward = false;
        }

        while (!up && !forward && scrabbleBoard.getSquarePosition(column, rowNumber) == initalPosition) {
            if (scrabbleBoard.getSquarePosition(column, rowNumber - 1).containsLetter()) {
                int newRowNumber = rowNumber;
                horzSubtractiveConnectedWord.add(scrabbleBoard.getSquarePosition(column, newRowNumber).getLetter());
                expandWordSearch(column, newRowNumber, false);
                rowNumber = row;
            }

            if (scrabbleBoard.getSquarePosition(column - 1, rowNumber).containsLetter()) {
                int newColumnNumber = column;
                vertSubtractiveConnectedWord.add(scrabbleBoard.getSquarePosition(newColumnNumber, rowNumber).getLetter());
                expandWordSearch(newColumnNumber, rowNumber, false);
                column = col;
            } else {
                return;
            }
        }
    }

    public void expandWordSearch(int col, int row, boolean direction) {
        int defaultCol = this.col;
        int defaultRow = this.row;
        foundConnectingWords = true;

        if (direction) {
            while (scrabbleBoard.getSquarePosition(col, ++row).containsLetter()) {
                horzAdditiveConnectedWord.add(scrabbleBoard.getSquarePosition(col, row).getLetter());
            }
            col = defaultCol;
            while (scrabbleBoard.getSquarePosition(++col, row).containsLetter()) {
                vertAdditiveConnectedWord.add(scrabbleBoard.getSquarePosition(col, row).getLetter());
            }
            row = defaultRow;
        }

        if (!direction) {
            while (scrabbleBoard.getSquarePosition(col, --row).containsLetter()) {
                horzSubtractiveConnectedWord.add(scrabbleBoard.getSquarePosition(col, row).getLetter());
            }
            col = defaultCol;
            while (scrabbleBoard.getSquarePosition(--col, row).containsLetter()) {
                vertSubtractiveConnectedWord.add(scrabbleBoard.getSquarePosition(col, row).getLetter());
            }
        }
    }

    private void clearExcessWords() {
        horzAdditiveConnectedWord.clear();
        horzSubtractiveConnectedWord.clear();
        vertAdditiveConnectedWord.clear();
        vertSubtractiveConnectedWord.clear();
    }

    public void getCurrentLetter(ScrabbleLetter scrabbleLetter) {
        this.currentLetter = scrabbleLetter;
    }

    public void getInitalPostion(Square initalPosition) {
        this.initalPosition = initalPosition;
    }
}


