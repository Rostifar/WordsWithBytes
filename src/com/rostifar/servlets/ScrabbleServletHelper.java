package com.rostifar.servlets;

//import com.google.gson.Gson;

import com.google.gson.Gson;
import com.rostifar.gamecontrol.ScrabbleGameException;
import com.rostifar.gamecontrol.ScrabbleGameManager;
import com.rostifar.scrabbleproject.Player;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *  This Servlet Helper class assists the Scrabble Game servlets when "talking" to the ScrabbleGame Java based domain model from a UI.
 *  Created by GitLazy (Dad) on 3/4/2016.
 */
public class ScrabbleServletHelper  {

    protected static ScrabbleGameManager getGameManagerFromSession(HttpServletRequest request) throws ServletException {
        HttpSession session = request.getSession();
        ScrabbleGameManager scrabbleGameManager = (ScrabbleGameManager) session.getAttribute("ScrabbleGameManager");

        if (scrabbleGameManager == null) {
            try {
                scrabbleGameManager = new ScrabbleGameManager();
            } catch (ScrabbleGameException exp) {
                throw new ServletException(exp.getMessage());
            }
        }
        return scrabbleGameManager;
    }

    /**
     * Store the instance of the GameManager relevant to the game in context on the user session
     */
   protected static void storeGameManagerOnSession(HttpServletRequest request, ScrabbleGameManager scrabbleGameManager) {
        HttpSession session = request.getSession();
        session.setAttribute("ScrabbleGameManager", scrabbleGameManager);
    }

    /**
     * Store the instance of the current player (player whose turn we are processing) on the user session.
     * TODO: Is there needed since current player is already in ScrabbleGameManager?
     */
    protected static void storeCurrentPlayerOnSession(HttpServletRequest request, Player currentPlayer) {
        HttpSession session = request.getSession();
        session.setAttribute("CurrentPlayer", currentPlayer);
    }

    /**
     * Convert instance of ScrabbleGameManager to it's JSON equivalent
     * @return a String containing the resulting JSON
     */
    protected static String getJSONforGameManager(ScrabbleGameManager gameManager) {
        Gson gson = new Gson();
        return gson.toJson(gameManager);
    }

    /**
     * Convert instance of ScrabbleGameManager's game code to it's JSON equivalent
     * @return a String containing the resulting JSON
     */
    protected static String getJSONforGameCode(ScrabbleGameManager gameManager) {
        Gson gson = new Gson();
        return gson.toJson(gameManager.getGameCode());
    }

    /**
     * Convert instance of Player to it's JSON equivalent
     * @return a String containing the resulting JSON
     */
    protected static String getJSONforCurrentPlayer(ScrabbleGameManager gameManager) {
        Gson gson = new Gson();
        return gson.toJson(gameManager.getCurrentPlayer());
   }

    /**
     * Convert instance of Player to it's JSON equivalent
     * @return a String containing the resulting JSON
     */
    protected static String getJSONforScrabbleBoard(ScrabbleGameManager gameManager) {
        Gson gson = new Gson();
        return gson.toJson(gameManager.getScrabbleBoard());
    }


}
