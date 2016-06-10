package com.rostifar.servlets;

import com.google.gson.Gson;
import com.rostifar.gamecontrol.ScrabbleGameCache;
import com.rostifar.gamecontrol.ScrabbleGameManager;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by rostifar on 6/4/16.
 */
public class ChangeGameStateServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        Gson gson = new Gson();
        String newGameState = request.getParameter("newGameState");
        ScrabbleGameManager scrabbleGameManager = ScrabbleGameCache.lookupGame(ScrabbleServletHelper.getGameCodeFromSession(request));

        scrabbleGameManager.setNewGameState(newGameState);
        scrabbleGameManager.getGameBroadcaster().broadcast(ScrabbleServletHelper.getJSONforGameManager(scrabbleGameManager));
    }
}
