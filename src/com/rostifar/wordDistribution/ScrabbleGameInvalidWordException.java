package com.rostifar.wordDistribution;

import com.rostifar.gamecontrol.ScrabbleGameException;

/**
 * Created by GitLazy (Dad) on 12/11/2015.
 *
 */
public class ScrabbleGameInvalidWordException extends ScrabbleGameException {
    public ScrabbleGameInvalidWordException(String msg) {
        super(msg);
    }

}
