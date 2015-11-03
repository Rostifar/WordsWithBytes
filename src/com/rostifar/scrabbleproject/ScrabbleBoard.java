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

        for (int row = 0; row <= ROW_LENGTH - 1; row += 7) {
            for (int col = 0; col < ROW_LENGTH; col += 7) {
                if (row == 7 && col == 7) //Skip over center square
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
                //board[3][7] = new Square(SquareEnum.DOUBLE_LETTER);
                // board[3][COLUMN_LENGTH - 1] = new Square(SquareEnum.DOUBLE_LETTER);
            }
        }

        //Row 6 and row 8 are the same
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
