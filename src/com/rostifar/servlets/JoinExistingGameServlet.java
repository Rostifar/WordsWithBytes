package com.rostifar.servlets;

import com.rostifar.gamecontrol.ScrabbleGameCache;
import com.rostifar.gamecontrol.ScrabbleGameManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet to add players to the Scrabble game.
 * Created by GitLazy on 3/21/2016.
 */
public class JoinExistingGameServlet extends javax.servlet.http.HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        String gameCode = request.getParameter("gameCode");
        String playerName = request.getParameter("playerID");
        ScrabbleGameManager gameManager = ScrabbleGameCache.lookupGame(gameCode);
        String json;

        //if (gameManager.getPlayers().contains())

        if (gameCode == null) //Could not find the Game Code in the cache
            json = "{Error - Game Code cannot be found}";
        else {
            json = ScrabbleServletHelper.getJSONforGameManager(gameManager);
        }

        System.out.println(this.getClass().getName() + "Returning JSON\n" + json);
        response.getWriter().write(json);
    }
}
