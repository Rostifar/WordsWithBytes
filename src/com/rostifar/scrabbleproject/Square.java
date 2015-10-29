package com.rostifar.scrabbleproject;

/**
 * Created by DAD on 10/4/2015.
 */
public class Square {
    private SquareEnum squareType;
    private ScrabbleLetter letter;


    public Square() {
        squareType = SquareEnum.REGULAR;
    }

    public Square(SquareEnum typeOfSquare) {
        squareType = typeOfSquare;
    }

    public boolean containsLetter() {
        return letter != null;
    }

    public boolean isDoubleWord() {
        return squareType.equals(SquareEnum.DOUBLE_WORD);
    }

    public boolean isTripleWord() {
        return squareType.equals(SquareEnum.TRIPLE_WORD);
    }

    public boolean isDoubleLetter() {
        return squareType.equals(SquareEnum.DOUBLE_LETTER);
    }

    public boolean isTripleLetter() {
        return squareType.equals(SquareEnum.TRIPLE_LETTER);
    }

    public boolean isCenterSquare() {
        return squareType.equals(SquareEnum.CENTER_STAR);
    }

    @Override
    public String toString() {
        return squareType.toString();
    }
}
