package com.rostifar.servlets;

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

        int numberOfPlayers = Integer.valueOf(request.getParameter("numberOfPlayers"));
        System.out.println(this.getClass().getName() + " - Adding " + numberOfPlayers + " players.");

        ScrabbleGameManager gameManager = ScrabbleServletHelper.getGameManagerFromSession(request);
        gameManager.addPlayers(numberOfPlayers);
        String json = ScrabbleServletHelper.getJSONforGameManager(gameManager);
        ScrabbleServletHelper.storeGameManagerOnSession(request, gameManager);
     //   System.out.println(this.getClass().getName() + "Returning JSON\n" + json);
        response.getWriter().write(json);
    }
}
