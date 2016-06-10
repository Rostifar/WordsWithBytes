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
        ScrabbleGameManager gameManager = ScrabbleGameCache.lookupGame(gameCode);
        String json;

        if (ScrabbleGameCache.lookupGame(gameCode) == null) //Could not find the Game Code in the cache
            json = "Error, Game doesn't exist";
        else if(ScrabbleGameCache.lookupGame(gameCode).getPlayers().size() == 4){
            json = "Error, Game lobby is full";
        } else {
            ScrabbleServletHelper.storeGameCodeOnSession(request, gameCode);
            json = ScrabbleServletHelper.getJSONforGameManager(gameManager);
        }
        System.out.println(this.getClass().getName() + "Returning JSON\n" + json);
        response.getWriter().write(json);
    }
}
