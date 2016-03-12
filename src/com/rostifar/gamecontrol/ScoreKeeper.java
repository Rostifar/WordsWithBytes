package com.rostifar.gamecontrol;

import com.rostifar.wordDistrobution.ScrabbleLetter;

import java.util.List;

/**
 * Created by D14048 on 10/4/2015.
 */
public class ScoreKeeper {
    private int totalPoints;
    private int totalPointValueForWord;

    public ScoreKeeper() {
        getplayerScore();
    }

    public void getWordPointValue(List<ScrabbleLetter> wordToBeScored, int wordPointValueScaleFactor) {

        for (ScrabbleLetter letter : wordToBeScored) {
            totalPointValueForWord += letter.getPointValue().getValue();
        }
        totalPoints += (totalPointValueForWord * wordPointValueScaleFactor);
        totalPointValueForWord = 0;
    }
    public int getplayerScore() {
        return totalPoints;
    }

}
