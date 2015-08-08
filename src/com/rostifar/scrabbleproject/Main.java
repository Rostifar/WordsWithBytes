package com.rostifar.scrabbleproject;

/**
 * Created by ross on 7/20/15.
 */
public class Main {
    public static void main(String[] args) {




            Main main1 = new Main();
        main1.newLetterSelection();

       // main1.newGUI();
        main1.newGameManager();
        main1.newGameGrid();

    }

    public void newLetterSelection() {
        LetterSelection letter = new LetterSelection();
        letter.setupLetter();
    }

    public void newGameGrid() {
        GameBoard gameboard = new GameBoard();
    }

    public void newGameManager() {
        GameManager gamemanager = new GameManager();

        gamemanager.setupInput();

    }

    public void newGUI() {
        GUI gui1 = new GUI();
        gui1.GUI_Setup();
    }





}
