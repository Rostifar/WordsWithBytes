package com.rostifar.gamecontrol;

import java.io.Serializable;

/**
 * Created by Dad on 11/13/2015.
 * This is a serializable Exception subclass that is the top level Exception for the Scrabble Game.
 */
public class ScrabbleGameException extends Exception implements Serializable{

    public ScrabbleGameException(String message) {
        super(message);
    }
}