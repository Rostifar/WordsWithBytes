package com.rostifar.servlets;

import com.google.gson.Gson;
import com.rostifar.gamecontrol.ScrabbleGameCache;
import com.rostifar.gamecontrol.ScrabbleGameManager;

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
        String gameCode = (String)request.getSession().getAttribute("GameCode");
        String playerUsername = request.getParameter("username");
        ScrabbleGameCache.lookupGame(gameCode).getGameManager().addPlayer(playerUsername, null);
        String playersJson = gson.toJson(ScrabbleGameCache.lookupGame(gameCode).getGameManager().getPlayers());
        ScrabbleGameCache.lookupGame(gameCode).getBroadcaster().broadcast(gson.toJson(ScrabbleGameCache.lookupGame(gameCode).getGameManager()));
        response.getWriter().write(playersJson);
    }
}
