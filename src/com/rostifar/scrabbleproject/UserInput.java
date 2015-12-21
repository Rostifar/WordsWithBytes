package com.rostifar.scrabbleproject;

import java.util.Scanner;

/**
 * Utility class to manage prompting the user for input.
 * Created by Dad on 11/20/2015.
 */
public class UserInput {


    public String getInputFromUser(String prompt) {
        if (prompt != null) {
            System.out.println(prompt);
        }

        Scanner userInput = new Scanner(System.in);
        return userInput.nextLine();
    }

    public String getInputFromUser() throws UserInputException {
        return getInputFromUser(null);
    }
}
