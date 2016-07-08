package com.rostifar.servlets;

import com.google.gson.Gson;
import com.rostifar.gamecontrol.ScrabbleGameCache;
import com.rostifar.gamecontrol.ScrabbleGameManager;
import com.rostifar.scrabbleproject.Player;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Servlet to add players to the Scrabble game.
 * Created by GitLazy on 3/21/2016.
 */
public class AddPlayerServlet extends javax.servlet.http.HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        Gson gson = new Gson();
        String gameCode = (String)request.getSession().getAttribute("GameCode");
        ScrabbleGameManager scrabbleGameManager = ScrabbleGameCache.lookupGame(gameCode).getGameManager();
        String playerUsername = request.getParameter("username");

        ScrabbleGameCache.lookupGame(gameCode).getGameManager().addPlayer(playerUsername, null);
        List<Player> gamePlayers = ScrabbleGameCache.lookupGame(gameCode).getGameManager().getPlayers();
        String playersJson = gson.toJson(gamePlayers.get(gamePlayers.size() - 1));
        response.getWriter().write(playersJson);
    }
}
