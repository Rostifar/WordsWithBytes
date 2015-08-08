package com.rostifar.scrabbleproject;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.*;




/**
 * Created by ross on 7/24/15.
 */
public class GUI extends JFrame{

    public void GUI_Setup() {

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {

                JFrame jframe1 = new JFrame("Scrabble");

                jframe1.setExtendedState(JFrame.MAXIMIZED_BOTH);
                jframe1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                jframe1.setVisible(true);


            }
        });









    }



}
