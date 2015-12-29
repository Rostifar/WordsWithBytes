package com.rostifar.scrabbleproject;

/**
 * Created by D14048 on 10/4/2015.
 */
public class ScoreKeeper {
    private int wordPointValue;
    private int totalPoints;

    public ScoreKeeper() {
        getplayerScore();
    }

    public void getWordPointValue(ScrabbleWord scrabbleWord) {
        int letterPointValue;
        PointValue pointValue;
        wordPointValue = 0;

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
