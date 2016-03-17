package com.rostifar.scabbleboard;

import com.rostifar.wordDistrobution.ScrabbleLetter;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by ross on 1/19/16.
 */
public class ScrabbleBoardMechanicsTest {
    ScrabbleBoard scrabbleBoard = new ScrabbleBoard();
    ScrabbleBoardMechanics scrabbleBoardMechanics = new ScrabbleBoardMechanics(scrabbleBoard);
    List<ScrabbleLetter> horzAdditiveConnectedWord = scrabbleBoardMechanics.getHorizontalPositive();
    List<ScrabbleLetter> horzSubtractiveConnectedWord = scrabbleBoardMechanics.getHorizontalNegative();
    List<ScrabbleLetter> vertAdditiveConnectedWord = scrabbleBoardMechanics.getVerticalPositive();
    List<ScrabbleLetter> vertSubtractiveConnectedWord = scrabbleBoardMechanics.getHorizontalNegative();

    ScrabbleLetter scrabbleLetter;
    ScrabbleLetter scrabbleLetter1;
    ScrabbleLetter scrabbleLetter2;
    ScrabbleLetter scrabbleLetter3;
    ScrabbleLetter scrabbleLetter4;
    ScrabbleLetter scrabbleLetter5;
    ScrabbleLetter scrabbleLetter6;
    ScrabbleLetter scrabbleLetter7;


    public void setUp() throws Exception {
        scrabbleLetter = new ScrabbleLetter('A');
        scrabbleLetter1 = new ScrabbleLetter('H');
        scrabbleLetter2 = new ScrabbleLetter('B');
        scrabbleLetter3 = new ScrabbleLetter('C');
        scrabbleLetter4 = new ScrabbleLetter('F');
        scrabbleLetter5 = new ScrabbleLetter('Z');
        scrabbleLetter6 = new ScrabbleLetter('P');
        scrabbleLetter7 = new ScrabbleLetter('Y');
        scrabbleBoard.addLetterToSquare(scrabbleLetter, 7, 7);
        scrabbleBoard.addLetterToSquare(scrabbleLetter1, 10, 7);
        scrabbleBoard.addLetterToSquare(scrabbleLetter2, 8, 7);
        scrabbleBoard.addLetterToSquare(scrabbleLetter3, 9, 7);
        scrabbleBoard.addLetterToSquare(scrabbleLetter4, 6, 8);
        scrabbleBoard.addLetterToSquare(scrabbleLetter5, 5, 7);
        scrabbleBoard.addLetterToSquare(scrabbleLetter6, 6, 6);
        scrabbleBoard.addLetterToSquare(scrabbleLetter7, 6, 5);
    }

    @Test
    public void testCheckForFirstLetter() throws Exception {
        setUp();
        scrabbleBoardMechanics.setRow(7);
        scrabbleBoardMechanics.setCol(6);
        scrabbleBoardMechanics.setOrientation("v");
        scrabbleBoardMechanics.checkForConnectingWords();

        assertEquals("The correct letter is:", scrabbleLetter7, horzSubtractiveConnectedWord.get(1));
        assertEquals("The Correct Letter is:", scrabbleLetter6, vertSubtractiveConnectedWord.get(0));
        assertEquals("The correct letter is:", scrabbleLetter4, horzAdditiveConnectedWord.get(0));
    }

    @Test
    public void testConnectMainWord() throws Exception {
        List<ScrabbleLetter> randomWord = new ArrayList<>();
        scrabbleBoardMechanics.setRow(4);
        scrabbleBoardMechanics.setCol(6);
        scrabbleBoardMechanics.setOrientation("h");
        ScrabbleLetter t = new ScrabbleLetter('T');
        ScrabbleLetter r = new ScrabbleLetter('R');
        ScrabbleLetter e1 = new ScrabbleLetter('E');
        ScrabbleLetter e2 = new ScrabbleLetter('E');
        randomWord.add(t);
        randomWord.add(r);
        randomWord.add(e1);
        randomWord.add(e2);
        setUp();
        scrabbleBoardMechanics.checkForConnectingWords();
        scrabbleBoardMechanics.getPlayedWord(randomWord);

     /*   assertEquals("The correct found word is: ", scrabbleLetter7, scrabbleBoardMechanics.getPrimaryWord().get(1));
        assertEquals("The correct found word is: ", r, scrabbleBoardMechanics.getPrimaryWord().get(3));
        assertEquals("The correct found word is: ", e2, scrabbleBoardMechanics.getPrimaryWord().get(6));
        assertEquals("The correct found word is: ", scrabbleLetter4, scrabbleBoardMechanics.getPrimaryWord().get(4));*/
    }

    @Test
    public void testConnectSecondaryWord() throws Exception {
        List<ScrabbleLetter> randomWord = new ArrayList<>();
        scrabbleBoardMechanics.setCol(4);
        scrabbleBoardMechanics.setRow(7);
        scrabbleBoardMechanics.setOrientation("v");
        ScrabbleLetter o1 = new ScrabbleLetter('O');
        ScrabbleLetter o2 = new ScrabbleLetter('O');
        randomWord.add(o1);
        randomWord.add(o2);

        setUp();
        scrabbleBoardMechanics.checkForConnectingWords();
        scrabbleBoardMechanics.getPlayedWord(randomWord);

        //assertEquals("The correct found letter is: ", scrabbleLetter7, scrabbleBoardMechanics.getSecondaryWord().get(0));
        //assertEquals("The correct found letter is: ", o2, scrabbleBoardMechanics.getSecondaryWord().get(2));
        //assertEquals("The correct found letter is: ", scrabbleLetter4, scrabbleBoardMechanics.getSecondaryWord().get(3));

    }

    @Test
    public void testUnusualDoubleWordIntersection() throws Exception{
        List<ScrabbleLetter> randomWord = new ArrayList<>();
        scrabbleBoardMechanics.setCol(5);
        scrabbleBoardMechanics.setRow(6);
        scrabbleBoardMechanics.setOrientation("h");
        ScrabbleLetter o = new ScrabbleLetter('O');
        randomWord.add(o);

        setUp();
        scrabbleBoardMechanics.checkForConnectingWords();
        scrabbleBoardMechanics.getPlayedWord(randomWord);

      //  assertEquals("The correct found letter is: ", scrabbleLetter5, scrabbleBoardMechanics.getPrimaryWord().get(1));
        //assertEquals("The correct found letter is: ", scrabbleLetter6, scrabbleBoardMechanics.getSecondaryWord().get(1));
        //assertEquals("The correct found letter is: ", o, scrabbleBoardMechanics.getSecondaryWord().get(0));
       /// assertEquals("The correct found letter is: ", o, scrabbleBoardMechanics.getPrimaryWord().get(0));
    }
}