package com.rostifar.servlets;

import com.google.gson.Gson;
import com.rostifar.gamecontrol.ScrabbleGameManager;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by ross on 4/25/16.
 */
public class AddLettersToRackServlet extends HttpServlet{
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {

        ScrabbleGameManager gameManager = ScrabbleServletHelper.getGameManagerFromSession(request);
        Gson gson = new Gson();

        String rackJson = gson.toJson(gameManager.getCurrentPlayer().getRack().getLettersOnRack());
        response.getWriter().write(rackJson);
    }
}
