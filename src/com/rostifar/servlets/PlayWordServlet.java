package com.rostifar.servlets;

import com.rostifar.gamecontrol.ScrabbleGameManager;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Git:Lazy (Dad) on 3/21/2016.
 * Servlet called by from end to process a player playing a word using back end.
 * playWord(char[] word, int col, int row, String orientation)
 */
public class PlayWordServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {

        String wordPlayed = request.getParameter("wordPlayed");
        String playedWordRows = request.getParameter("letterPositionsRow");
        String playedWordCols = request.getParameter("letterPositionsCol");
        String orientation = request.getParameter("wordOrientation");
        String blankLetters = request.getParameter("blankLetters");

        System.out.println(this.getClass().getName() + " - Play word: " + wordPlayed);

        ScrabbleGameManager gameManager = ScrabbleServletHelper.getGameManagerFromSession(request);
        gameManager.playWord(wordPlayed.toCharArray(), Integer.valueOf(playedWordRows), Integer.valueOf(playedWordCols), orientation, blankLetters.toCharArray());
        String json = ScrabbleServletHelper.getJSONforGameManager(gameManager);
        ScrabbleServletHelper.storeGameManagerOnSession(request, gameManager);
        System.out.println(this.getClass().getName() + "Returning JSON\n" + json);
        response.getWriter().write(json);
    }
}
