package com.rostifar.gamecontrol;

import com.rostifar.wordDistrobution.ScrabbleLetter;
import com.rostifar.wordDistrobution.ScrabbleWord;
import com.rostifar.wordDistrobution.PointValue;

/**
 * Created by D14048 on 10/4/2015.
 */
public class ScoreKeeper {
    private int totalPoints;

    public ScoreKeeper() {
        getplayerScore();
    }

    public void getWordPointValue(ScrabbleWord scrabbleWord) {
        int letterPointValue;
        PointValue pointValue;
        int wordPointValue = 0;

        for (int i = 0; i < scrabbleWord.getNumberOfLetters(); i++) {
            ScrabbleLetter scrabbleLetter = scrabbleWord.getLetterAt(i);
            pointValue = new PointValue(scrabbleLetter.getLetter());
            letterPointValue = pointValue.getValue();
            wordPointValue += letterPointValue;
        }
        totalPoints += wordPointValue;
    }

    public int getplayerScore() {
        return totalPoints;
    }

}
