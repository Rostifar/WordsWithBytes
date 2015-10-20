package com.rostifar.scrabbleproject;

/**
 * Created by ross on 10/19/15.
 */
public class RoundKeeper {

    protected int numberofturns;


    GameManager gameManager;



    int roundKeeper(){

        if (gameManager.isfirstround == true) {
            numberofturns = 0;

            for (numberofturns = 0; numberofturns < 100; numberofturns ++) {

                if (numberofletterleft == 0 || numberofspacesremaining == 0) {
                    return numberofturns;
                }
                else if(isgameover == true){
                    return numberofturns;
                }
            }



        }
    }
}
