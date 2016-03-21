package com.rostifar.servlets;

import com.rostifar.scrabbleproject.Player;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet to add players to the scrabble game.
 * Created by GitLazy on 3/21/2016.
 */
public class AddPlayerServlet extends ScrabbleGameControllerServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        super.doPost(request, response);

        String playerName  = request.getParameter("PlayerName");

        if (playerName == null)
            playerName = "UnnamedPlayer-" + System.currentTimeMillis();

        Player player = new Player(playerName);
        storePlayerOnSession(request, player);
    }
}
