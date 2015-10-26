package com.rostifar.scrabbleproject;

/**
 * Created by Dad on 10/4/2015.
 */
public class ScrabbleBoard {
    public static final int ROW_LENGTH = 15;
    public static final int COLUMN_LENGTH = 15;

    private Square[][] board = new Square[COLUMN_LENGTH][ROW_LENGTH];

    public void setupBoard() {
        for (int col = 0; col < COLUMN_LENGTH; col++) {
            for (int row = 0; col < ROW_LENGTH; row++) {
                switch (col) {
                    case 1:
                        break;
                    default:
                        break;
                }
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int col = 0; col < COLUMN_LENGTH; col++) {
            for (int row = 0; col < ROW_LENGTH; row++) {

            }
        }
        return stringBuilder.toString();
    }
}
