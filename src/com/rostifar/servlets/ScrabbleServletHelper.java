package com.rostifar.servlets;

//import com.google.gson.Gson;

import com.google.gson.Gson;
import com.rostifar.gamecontrol.ScrabbleGameException;
import com.rostifar.gamecontrol.ScrabbleGameManager;
import com.rostifar.gamecontrol.ScrabbleGameResources;
import com.rostifar.scrabbleproject.Player;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *  This Servlet Helper class assists the Scrabble Game servlets when "talking" to the ScrabbleGame Java based domain model from a UI.
 *  Created by GitLazy (Dad) on 3/4/2016.
 */
public class ScrabbleServletHelper  {

    protected static void storeGameCodeOnSession(HttpServletRequest request, String gameCode) throws ServletException {
        HttpSession session = request.getSession();
        session.setAttribute("GameCode", gameCode);
    }

    protected static String getGameCodeFromSession(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return (String)session.getAttribute("GameCode");
    }

    /**
     * Store the instance of the GameManager relevant to the game in context on the user session
     * @deprecated
     */
   protected static void storeGameManagerOnSession(HttpServletRequest request, ScrabbleGameManager scrabbleGameManager) {
        HttpSession session = request.getSession();
        session.setAttribute("ScrabbleGameManager", scrabbleGameManager);
    }

    /**
     * Store the instance of the current player (player whose turn we are processing) on the user session.
     * TODO: Is there needed since current player is already in ScrabbleGameManager?
     * @deprecated
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
    protected static String getJSONforGameCode(String gameCode) {
        Gson gson = new Gson();
        return gson.toJson(gameCode);
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
