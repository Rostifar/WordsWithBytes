package com.rostifar.gamecontrol;

import com.rostifar.wordDistribution.ScrabbleLetter;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Dad on 10/4/2015.
 */
public class ScoreKeeper implements Serializable {
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
    }
    public int getplayerScore() {
        return totalPoints;
    }

}
