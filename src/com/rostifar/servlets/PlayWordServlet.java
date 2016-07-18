package com.rostifar.servlets;

import com.rostifar.gamecontrol.ScrabbleGameCache;
import com.rostifar.gamecontrol.ScrabbleGameManager;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Git:Lazy (Dad) on 3/21/2016.
 * Servlet called by from end to process a player playing a word using back end.
 * playWord(char[] word, int col, int row, String orientation)
 */
public class PlayWordServlet extends HttpServlet {

    private List<Integer> convertStringToIntArray(String numString) {
        List<Integer> positions = new ArrayList<>();
        String number = "";

        for (char num : numString.toCharArray()) {
            if (num == ',') {
                positions.add(Integer.valueOf(number));
                number = "";
            } else {
                number = number + num;
                System.out.println(number);
            }
        }
        positions.add(Integer.valueOf(number));
        return positions;
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        String wordPlayed = request.getParameter("wordPlayed");
        String playedWordRows = request.getParameter("letterPositionsRow");
        String playedWordCols = request.getParameter("letterPositionsCol");
        String orientation = request.getParameter("wordOrientation");
        String blankLetters = request.getParameter("blankLetters");
        System.out.println(this.getClass().getName() + " - Play word: " + wordPlayed);

        ScrabbleGameManager gameManager = ScrabbleGameCache.lookupGame(ScrabbleServletHelper.getGameCodeFromSession(request)).getGameManager();
        boolean isWordValid = gameManager.playWord(wordPlayed.toCharArray(), convertStringToIntArray(playedWordCols), convertStringToIntArray(playedWordRows), orientation, blankLetters.toCharArray());
        String json = isWordValid ? ScrabbleServletHelper.getJSONforGameManager(gameManager) : "invalid";
        response.getWriter().write(json);
    }
}
