package com.rostifar.servlets;

import com.google.gson.Gson;
import com.rostifar.gamecontrol.ScrabbleGameCache;
import com.rostifar.gamecontrol.ScrabbleGameManager;
import org.atmosphere.cpr.Broadcaster;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet to add players to the Scrabble game.
 * Created by GitLazy on 3/21/2016.
 */
public class AddPlayerServlet extends javax.servlet.http.HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        Gson gson = new Gson();
        String playerUsername = request.getParameter("username");
        ScrabbleGameManager scrabbleGameManager = ScrabbleGameCache.lookupGame(ScrabbleServletHelper.getGameCodeFromSession(request));
        scrabbleGameManager.addPlayer(playerUsername, null);
        String playersJson = gson.toJson(scrabbleGameManager.getPlayers());
        response.getWriter().write(playersJson);
    }
}
