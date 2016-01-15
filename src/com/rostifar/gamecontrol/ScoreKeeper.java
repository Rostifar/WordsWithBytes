package com.rostifar.gamecontrol;

import com.rostifar.wordDistrobution.ScrabbleLetter;
import com.rostifar.wordDistrobution.ScrabbleWord;
import com.rostifar.wordDistrobution.PointValue;

import java.util.List;

/**
 * Created by D14048 on 10/4/2015.
 */
public class ScoreKeeper {
    private int totalPoints;

    public ScoreKeeper() {
        getplayerScore();
    }

    public void getWordPointValue(List<ScrabbleLetter> scrabbleWord) {
        int letterPointValue;
        PointValue pointValue;
        int wordPointValue = 0;

        for (ScrabbleLetter letter : scrabbleWord) {
            pointValue = new PointValue(letter.getLetter());
            letterPointValue = pointValue.getValue();
            wordPointValue += letterPointValue;
        }
        totalPoints += wordPointValue;
    }

    public int getplayerScore() {
        return totalPoints;
    }

}
