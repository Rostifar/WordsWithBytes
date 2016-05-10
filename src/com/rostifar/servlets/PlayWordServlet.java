package com.rostifar.servlets;

import com.rostifar.gamecontrol.ScrabbleGameManager;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Array;

/**
 * Created by Git:Lazy (Dad) on 3/21/2016.
 * Servlet called by from end to process a player playing a word using back end.
 */
public class PlayWordServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {

        String[] wordPlayed = request.getParameterValues("wordPlayed");
        String[] playedWordRows = request.getParameterValues("letterPositionsRow");
        String[] playedWordCols = request.getParameterValues("letterPositionsCol");
        String orientation = request.getParameter("wordOrientation");
        int[] wordCols = new int[playedWordCols.length];
        int[] wordRows = new int[playedWordRows.length];

        for (int i = 0; i < request.getParameterValues("letterPositionsRow").length; i++) {
            wordCols[i] = Integer.valueOf(playedWordCols[i]);
            wordRows[i] = Integer.valueOf(playedWordRows[i]);
        }

        System.out.println(this.getClass().getName() + " - Play word: " + "scrabbleWordInput");

        ScrabbleGameManager gameManager = ScrabbleServletHelper.getGameManagerFromSession(request);
        gameManager.playWord();
        String json = ScrabbleServletHelper.getJSONforGameManager(gameManager);
        ScrabbleServletHelper.storeGameManagerOnSession(request, gameManager);
        System.out.println(this.getClass().getName() + "Returning JSON\n" + json);
        response.getWriter().write(json);
    }
}
