package com.rostifar.scabbleboard;
import com.rostifar.wordDistrobution.ScrabbleLetter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ross on 1/16/16.
 */
public class ScrabbleBoardMechanics {

    private ScrabbleBoard scrabbleBoard;
    private int col;
    private int row;
    private String orientation;
    private List<ScrabbleLetter> horzAdditiveConnectedWord;
    private List<ScrabbleLetter> horzSubtractiveConnectedWord;
    private List<ScrabbleLetter> vertAdditiveConnectedWord;
    private List<ScrabbleLetter> vertSubtractiveConnectedWord;
    private boolean forward = true;
    private boolean up = true;
    private boolean foundConnectingWords;

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

    public void checkForConnectingWords() {
        checkForFirstLetter();
        if (foundConnectingWords) {

        }
    }

    public void checkForFirstLetter() {
        int column = this.col;
        int rowNumber = this.row;
        horzAdditiveConnectedWord = new ArrayList<>();
        horzSubtractiveConnectedWord = new ArrayList<>();
        vertAdditiveConnectedWord = new ArrayList<>();
        vertSubtractiveConnectedWord = new ArrayList<>();
        boolean forward = this.forward;
        boolean up = this.up;

        while (up && forward) {

            if (scrabbleBoard.getSquarePosition(column, rowNumber + 1).containsLetter()) {
                int newRowNumber = rowNumber;
                rowNumber = row;
                horzAdditiveConnectedWord.add(scrabbleBoard.getSquarePosition(column, newRowNumber).getLetter());
                expandWordSearch(col, newRowNumber, true);
            }
            if (scrabbleBoard.getSquarePosition(column + 1, rowNumber).containsLetter()) {
                int newColNumber = column;
                column = col;
                vertAdditiveConnectedWord.add(scrabbleBoard.getSquarePosition(newColNumber, rowNumber).getLetter());
                expandWordSearch(newColNumber, rowNumber, true);
            }
            up = false;
            forward = false;
        }

        while (!up && !forward) {
            if (scrabbleBoard.getSquarePosition(column, rowNumber - 1).containsLetter()) {
                int newRowNumber = rowNumber;
                rowNumber = row;
                horzSubtractiveConnectedWord.add(scrabbleBoard.getSquarePosition(column, newRowNumber).getLetter());
                expandWordSearch(column, newRowNumber, false);
            }

            if (scrabbleBoard.getSquarePosition(column - 1, rowNumber).containsLetter()) {
                int newColumnNumber = column;
                column = col;
                vertSubtractiveConnectedWord.add(scrabbleBoard.getSquarePosition(newColumnNumber, rowNumber).getLetter());
                expandWordSearch(newColumnNumber, rowNumber, false);
            } else {
                return;
            }
        }
    }

    public void expandWordSearch(int col, int row, boolean direction) {
        int defaultCol = this.col + 1;
        int defaultRow = this.row + 1;
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
        }else{
            return;
        }

        if (!direction) {
            while (scrabbleBoard.getSquarePosition(col, --row).containsLetter()) {
                horzSubtractiveConnectedWord.add(scrabbleBoard.getSquarePosition(col, row).getLetter());
            }
            col = defaultCol;
            while (scrabbleBoard.getSquarePosition(--col, row).containsLetter()) {
                vertSubtractiveConnectedWord.add(scrabbleBoard.getSquarePosition(col, row).getLetter());
            }
            row = defaultRow;

        }else{
            return;
        }
    }

}


