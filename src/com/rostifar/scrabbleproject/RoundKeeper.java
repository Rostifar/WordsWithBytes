package com.rostifar.scrabbleproject;

/**
 * Created by ross on 10/19/15.
 */
public class RoundKeeper {

    protected int numberofturns;


    GameManager gameManager;
    ScrabbleLetter scrabbleLetter;
    ScrabbleBoard scrabbleBoard;


    int roundKeeper() {

        if (gameManager.isfirstround == true) {
            numberofturns = 0;

            for (numberofturns = 0; numberofturns < 100; numberofturns++) {

                if (scrabbleLetter.numberoflettersleft == 0 || scrabbleBoard.numberofspacesremaining == 0) {
                    return numberofturns;
                } else if (gameManager.isgameover == true) {
                    return numberofturns;
                }
            }


        }
        return numberofturns;
    }
}

