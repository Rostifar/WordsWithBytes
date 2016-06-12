package com.rostifar.servlets;

import com.rostifar.gamecontrol.*;
import org.atmosphere.cpr.*;

import javax.inject.Inject;
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
        ScrabbleGameResources scrabbleGameResources = new ScrabbleGameResources();
        String gameCode = scrabbleGameResources.getGameCode();
        Broadcaster broadcaster = (new DefaultBroadcasterFactory()).get(DefaultBroadcaster.class, gameCode);
        ScrabbleGameManager gameManager;

        try {
            gameManager = new ScrabbleGameManager();
            ScrabbleGameCache.addGame(gameCode, new GameCacheValues(gameManager, broadcaster));
        } catch (ScrabbleGameException exp) {
            exp.printStackTrace();
            throw new ServletException(exp);
        }
        ScrabbleServletHelper.storeGameCodeOnSession(request, scrabbleGameResources.getGameCode());
        String json = ScrabbleServletHelper.getJSONforGameCode(scrabbleGameResources.getGameCode());
        response.getWriter().write(json);
    }
}
