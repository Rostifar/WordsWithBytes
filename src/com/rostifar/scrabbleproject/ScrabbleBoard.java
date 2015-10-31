package com.rostifar.scrabbleproject;

/**
 * Created by Dad on 10/4/2015.
 */
public class ScrabbleBoard {
    public static final int ROW_LENGTH = 15;
    public static final int COLUMN_LENGTH = 15;

    private Square[][] board = new Square[COLUMN_LENGTH][ROW_LENGTH];

    public ScrabbleBoard() {
        setupBoard();
    }

    private void setupBoard() {
        setupDoubleWords();
        setupTripleWords();
        setupDoubleLetters();
        setupTripleLetters();
        setupCenterSquare();
        setupDefaultSquares();

    }

    private void setupDefaultSquares() {
        //Set any square not already assigned to a plain regular space
        for (int col = 0; col < COLUMN_LENGTH; col++) {
            for (int row = 0; row < ROW_LENGTH; row++) {
                if (board[col][row] == null)
                    board[col][row] = new Square(SquareEnum.REGULAR);
            }
        }
    }

    private void setupCenterSquare() {
        board[7][7] =  new Square(SquareEnum.CENTER_STAR);
    }

    /**
     * Setup the double word squares on the board.
     */
    private void setupDoubleWords() {
        for (int row =  1, col =1 ;  row < ROW_LENGTH; row++, col++) {

            if (row >= 5 && row <= 8)
                continue; //Skip middle of board per board layout

            board[row][col] = new Square(SquareEnum.DOUBLE_WORD);
            board[row][(COLUMN_LENGTH  - 1) - col] = new Square(SquareEnum.DOUBLE_WORD);
        }
    }

    private void setupTripleWords() {
        board[0][0] = new Square(SquareEnum.TRIPLE_WORD);
        board[0][7] = new Square(SquareEnum.TRIPLE_WORD);
        board[0][COLUMN_LENGTH - 1] = new Square(SquareEnum.TRIPLE_WORD);
        board[COLUMN_LENGTH - 1][0] = new Square(SquareEnum.TRIPLE_WORD);
        board[COLUMN_LENGTH - 1][7] = new Square(SquareEnum.TRIPLE_WORD);
        board[COLUMN_LENGTH - 1][COLUMN_LENGTH - 1] = new Square(SquareEnum.TRIPLE_WORD);
    }


    private void setupDoubleLetters() {
        board[0][3]  = new Square(SquareEnum.DOUBLE_LETTER);
        board[0][11] = new Square(SquareEnum.DOUBLE_LETTER);
        board[3][6]  = new Square(SquareEnum.DOUBLE_LETTER);
        board[3][8]  = new Square(SquareEnum.DOUBLE_LETTER);
        board[4][0] = new Square(SquareEnum.DOUBLE_LETTER);
        board[4][7] = new Square(SquareEnum.DOUBLE_LETTER);
        board[4][COLUMN_LENGTH - 1] = new Square(SquareEnum.DOUBLE_LETTER);
        //TODO: This only does the top part of the board!!! HACK!
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
    @Override
    /**
     * Print the board and it's current contents to console using basic characters
     */
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\t  1  2  3  4  5  6  7  8  9 10 11 12 13 14 15\n");
        stringBuilder.append("\t----------------------------------------------\n");

        for (int col = 0; col < COLUMN_LENGTH; col++) {
            stringBuilder.append(col + 1).append("\t|");

            for (int row = 0; row < ROW_LENGTH; row++) {
                stringBuilder.append(board[col][row]).append('|');
            }

            stringBuilder.append("\n");
        }

        stringBuilder.append("\t----------------------------------------------\n");

        return stringBuilder.toString();
    }
}
