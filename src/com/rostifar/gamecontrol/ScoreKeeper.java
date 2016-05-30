package com.rostifar.gamecontrol;

import com.rostifar.wordDistribution.ScrabbleLetter;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Dad on 10/4/2015.
 */
public class ScoreKeeper implements Serializable {
    private int totalPoints;

    public ScoreKeeper() {
        totalPoints = 0;
    }

    public void addPoints(int pointValue) {
        totalPoints += pointValue;
    }

    public int getPlayerScore() {
        return totalPoints;
    }
}
