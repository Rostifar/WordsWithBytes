package com.rostifar.gamecontrol;

/**
 * Created by ross on 7/20/15. entry point to set a new game in motion
 */
public class Main {
    public static void main(String[] args) {

        Main main = new Main();
        try {
            main.startGame();
        } catch (ScrabbleGameException gameExp) {
            System.out.println("Could not start game due to failure: " + gameExp.getMessage());
            gameExp.printStackTrace();
        }
    }

    public void startGame() throws ScrabbleGameException{
       GameManager gameManager = new ScrabbleGameManager();
       gameManager.runGame();
    }

}
