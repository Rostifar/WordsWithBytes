package com.rostifar.scrabbleproject;

/**
 * Class to model a Square on the scrabble board. The Square has knowledge about it's score value (triple word, etc)
 * and any letter contained therein.
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

    public void setLetter(ScrabbleLetter aScrabbleLetter) throws ScrabbleGameException {
        if (containsLetter())
            throw new ScrabbleGameException("Space on board is already occupied by letter:" + getLetter());

    }

    public ScrabbleLetter getLetter() {
        return letter;
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

        if (this.containsLetter())
            return String.valueOf(letter);

        return squareType.toString();
    }
}
