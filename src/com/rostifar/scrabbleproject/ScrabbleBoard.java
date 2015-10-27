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
        board[0][0] = new Square(SquareEnum.TRIPLE_WORD);
        board[0][7] = new Square(SquareEnum.TRIPLE_WORD);
        board[0][COLUMN_LENGTH - 1] = new Square(SquareEnum.TRIPLE_WORD);
        board[COLUMN_LENGTH - 1][0] = new Square(SquareEnum.TRIPLE_WORD);
        board[COLUMN_LENGTH - 1][7] = new Square(SquareEnum.TRIPLE_WORD);
        board[COLUMN_LENGTH - 1][COLUMN_LENGTH - 1] = new Square(SquareEnum.TRIPLE_WORD);

        board[0][3] = new Square(SquareEnum.DOUBLE_LETTER);
        board[0][11] = new Square(SquareEnum.DOUBLE_LETTER);


        //Set any square not already assigned to a plain space
        for (int col = 0; col < COLUMN_LENGTH; col++) {
            for (int row = 0; row < ROW_LENGTH; row++) {
                if (board[col][row] == null)
                    board[col][row] = new Square(SquareEnum.PLAIN);
            }
        }
    }

    @Override
    /**
     * Print the board and it's current contents to console using basic characters
     */
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("______________________________\n");

        for (int col = 0; col < COLUMN_LENGTH; col++) {
            stringBuilder.append('|');
            for (int row = 0; row < ROW_LENGTH; row++) {
                stringBuilder.append(board[col][row]).append('|');
            }

            stringBuilder.append("\n");
        }

    //    stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        stringBuilder.append("______________________________\n");

        return stringBuilder.toString();
    }
}
