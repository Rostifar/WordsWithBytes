package com.rostifar.scrabbleproject;

import com.rostifar.gamecontrol.ScrabbleGameException;

/**
 * Created by Dad on 11/20/2015.
 */
public class UserInputException extends ScrabbleGameException {
    public UserInputException(String msg) {
        super(msg);
    }
}
