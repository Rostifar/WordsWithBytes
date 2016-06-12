package com.rostifar.servlets;

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
        String gameCode = (String)request.getSession().getAttribute("GameCode");
        String playerUsername = request.getParameter("username");
        Broadcaster broadcaster = (Broadcaster)request.getSession().getAttribute("GameBroadcaster");
        System.out.println(broadcaster);
        ScrabbleGameManager scrabbleGameManager = ScrabbleGameCache.lookupGame(ScrabbleServletHelper.getGameCodeFromSession(request));
        scrabbleGameManager.addPlayer(playerUsername, null);
    }
}
