package com.rostifar.servlets;

import com.rostifar.gamecontrol.ScrabbleGameCache;
import com.rostifar.gamecontrol.ScrabbleGameException;
import com.rostifar.gamecontrol.ScrabbleGameManager;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet to add players to the Scrabble game.
 * Created by GitLazy on 3/21/2016.
 */
public class StartNewGameServlet extends javax.servlet.http.HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {

        ScrabbleGameManager gameManager = ScrabbleServletHelper.getGameManagerFromSession(request);
        try {
            ScrabbleGameCache.addGame(gameManager);
            //gameManager.setUpBroadcaster();
        } catch (ScrabbleGameException exp) {
            exp.printStackTrace();
            throw new ServletException(exp);
        }
        //  gameManager.addPlayers(numberOfPlayers);
        String json = ScrabbleServletHelper.getJSONforGameCode(gameManager);
        ScrabbleServletHelper.storeGameManagerOnSession(request, gameManager);
     //   System.out.println(this.getClass().getName() + "Returning JSON\n" + json);
        response.getWriter().write(json);
    }
}
