package com.rostifar.scrabbleproject;

import java.lang.reflect.Field;
import java.nio.charset.Charset;

/**
 * Created by ross on 7/20/15.
 */
public class Main {
    public static void main(String[] args) {

        System.setProperty("file.encoding", "UTF-8");
        try {
            Field charset = Charset.class.getDeclaredField("defaultCharset");
            charset.setAccessible(true);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        Main main = new Main();
        main.setupGame();
    }

    public void setupGame() {

        //ScrabbleAlphabetImpl scrabbleAlphabet = new ScrabbleAlphabetImpl();
        ScrabbleBoard board = new ScrabbleBoard();
        System.out.println(board);


    }





}
