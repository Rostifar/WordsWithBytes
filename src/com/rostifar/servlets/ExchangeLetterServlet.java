package com.rostifar.servlets;

import com.rostifar.gamecontrol.ScrabbleGameCache;
import com.rostifar.gamecontrol.ScrabbleGameManager;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by rostifar on 5/31/16.
 */
public class ExchangeLetterServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        String lettersToExchange = request.getParameter("lettersToExchange");
        ScrabbleGameManager gameManager = ScrabbleGameCache.lookupGame(ScrabbleServletHelper.getGameCodeFromSession(request)).getGameManager();
        System.out.println(lettersToExchange);
        gameManager.exchangeLetters(lettersToExchange.toCharArray());
        String json = ScrabbleServletHelper.getJSONforGameManager(gameManager);
        response.getWriter().write(json);
    }
}
