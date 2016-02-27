package com.rostifar.scabbleboard;

/**
 * Created by ross on 1/25/16.
 */
public class NonConnectingPlayException extends Exception {
    @Override
    public String getMessage() {
        return "Error! The location you placed your word does not connect to a previously played word. Please try again";    }
}
