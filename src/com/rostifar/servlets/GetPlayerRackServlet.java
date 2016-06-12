package com.rostifar.servlets;

import com.google.gson.Gson;
import com.rostifar.gamecontrol.ScrabbleGameCache;
import com.rostifar.gamecontrol.ScrabbleGameManager;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by ross on 4/25/16.
 */
public class GetPlayerRackServlet extends HttpServlet{
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {

        System.out.println(this.getClass().getName() + " Called\n" + request);
        ScrabbleGameManager gameManager = ScrabbleGameCache.lookupGame(ScrabbleServletHelper.getGameCodeFromSession(request)).getGameManager();
        Gson gson = new Gson();

        String rackJson = gson.toJson(gameManager.getCurrentPlayer().getRack().getCharactersOnRack());
        response.getWriter().write(rackJson);
        System.out.println(this.getClass().getName() + "Returning JSON\n" + rackJson);
    }
}
