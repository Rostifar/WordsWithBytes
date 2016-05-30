package com.rostifar.scabbleboard;

import com.rostifar.gamecontrol.ScrabbleGameException;
import com.rostifar.wordDistribution.ScrabbleLetter;

import java.io.Serializable;

/**
 * Class to model a Square on the scrabble board. The Square has knowledge about it's score value (triple word, etc)
 * and any letter contained therein.
 * Created by DAD on 10/4/2015.
 */
public class Square implements Serializable {
    private SquareEnum squareType;
    private ScrabbleLetter letter;

    public Square() {
        squareType = SquareEnum.REGULAR;
    }

    public Square(SquareEnum typeOfSquare) {
        squareType = typeOfSquare;
    }

    public SquareEnum getSquareType(){
        return squareType;
    }

    public boolean containsLetter() {
        return letter != null;
    }

    public void setLetter(ScrabbleLetter aScrabbleLetter) throws ScrabbleGameException {
        if (containsLetter())
            throw new ScrabbleGameException("Space on board is already occupied by letter:" + getLetter());

        letter = aScrabbleLetter;
        squareType = SquareEnum.REGULAR;
    }

    public ScrabbleLetter getLetter() {
        return letter;
    }


    public boolean isSpecialSquare() {
        return isCenterSquare() || isDoubleLetter() || isTripleLetter() || isTripleWord() || isDoubleWord();
    }


    private boolean isDoubleWord() {
        return squareType.equals(SquareEnum.DOUBLE_WORD);
    }

    private boolean isTripleWord() {
        return squareType.equals(SquareEnum.TRIPLE_WORD);
    }

    private boolean isDoubleLetter() {
        return squareType.equals(SquareEnum.DOUBLE_LETTER);
    }

    private boolean isTripleLetter() {
        return squareType.equals(SquareEnum.TRIPLE_LETTER);
    }

    private boolean isCenterSquare() {
        return squareType.equals(SquareEnum.CENTER_STAR);
    }

    @Override
    public String toString() {

        if (this.containsLetter())
            return String.valueOf(" " + letter.getLetter());

        return squareType.toString();
    }
}
