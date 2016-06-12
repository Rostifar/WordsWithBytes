package com.rostifar.servlets;


import com.google.gson.Gson;
import com.rostifar.gamecontrol.ScrabbleGameCache;
import com.rostifar.gamecontrol.ScrabbleGameManager;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by ross on 5/9/16.
 */
public class GetPlayerScore extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {

        ScrabbleGameManager gameManager = ScrabbleGameCache.lookupGame(ScrabbleServletHelper.getGameCodeFromSession(request)).getGameManager();
        Gson gson = new Gson();
        int numberOfPlayers = gameManager.getPlayers().size();
        String[] playerScoreJSON = new String[numberOfPlayers];

        for (int i = 0; i < numberOfPlayers; i++) {

        }
    }
}

