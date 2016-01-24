package com.rostifar.scabbleboard;

import com.rostifar.gamecontrol.ScrabbleGameException;
import com.rostifar.wordDistrobution.ScrabbleLetter;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

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
    public void testExpandWordSearch() throws Exception {

    }
}